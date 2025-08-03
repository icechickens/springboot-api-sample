# 参考
https://github.com/kentny/docker-simple-cicd-demo

# 構成
- Dockerホスト(Dockerデーモン)
  - 公開ポート
    - 3000 → 3000
    - 8080 → 8080
  - Custom Bridge
    - WEBコンテナ(3000)
      - React
    - ↓
    - APIコンテナ(8080)
      - Spring Boot
    - ↓
    - DBコンテナ(5432)
      - PostgreSQL
    - ↓
    - Volume(db-storage)
- ローカルホスト
  - http://{Dockerホスト}:8080/api/hello?lang=ja

# Dockerで実行
## 全体(本番時)
```
# リソース作成
d volume create hello-web-prd-storage
d network create hello-web-prd-network

# APIイメージビルド
dimg build --target prd -t hello-web-prd-api-img:latest api/

# WEBイメージビルド
dimg build --target prd \
  --build-arg REACT_APP_API_SERVER=http://localhost:8080/api \
  -t hello-web-prd-web-img:latest web/

# DB起動
dcnt run -d --rm \
  --name hello-web-db \
  -p 5432:5432 \
  -e POSTGRES_PASSWORD=password \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_DB=appdb \
  -v hello-web-prd-storage:/var/lib/postgresql/data \
  -v ./db/initdb:/docker-entrypoint-initdb.d \
  --network hello-web-prd-network \
  postgres:15

# API起動
dcnt run -d --rm \
  --name hello-web-api \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://hello-web-db:5432/appdb \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=password \
  --network hello-web-prd-network \
  hello-web-prd-api-img:latest

# WEB起動
dcnt run -d --rm \
  --name hello-web-web \
  -p 80:80 \
  --network hello-web-prd-network \
  hello-web-prd-web-img:latest

```

## SpringのAPIのみ
```
$ dimg build -t my-api-img .
$ dimg ls | grep api
my-api-img            latest    7923960f0b55   34 seconds ago   1.18GB
$ dcnt run -p 8080:8080 --rm my-api-img
```

# Docker Composeで実行(開発時)
## 全体実行
### ソース修正なし(前回作成イメージを使用)
```
$ dc up
```

### ソース修正あり(イメージビルドを再実施)
```
$ dc up --build
```

## DBのみ起動
```
$ dc up db -d
$ dc exec db bash
root@3a370260442f:/# psql -U postgres
psql (15.13 (Debian 15.13-1.pgdg120+1))
Type "help" for help.

postgres=# \c appdb
You are now connected to database "appdb" as user "postgres".
appdb=# \dt
           List of relations
 Schema |   Name    | Type  |  Owner
--------+-----------+-------+----------
 public | greetings | table | postgres
(1 row)

appdb=# select * from greetings;
 id | lang |    text    
----+------+------------
  1 | ja   | こんにちは
  2 | en   | Hello
(2 rows)

# DB停止
dc down

```