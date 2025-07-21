# 構成
- Dockerホスト(Dockerデーモン)
  - 公開ポート
    - 8080 → 8080
  - Custom Bridge
    - APIコンテナ(8080)
    - ↓
    - DBコンテナ(5432)
      - Volume 
- ローカルホスト
  - http://{Dockerホスト}:8080/api/hello?lang=ja

# Dockerで実行
## 全体
```
d volume create api_db-storage
d network create api_default
dimg build -t my-api-img .

# DB起動
dcnt run --rm \
    --name my-db \
    -p 5432:5432 \
    -e POSTGRES_PASSWORD=password \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_DB=appdb \
    -v api_db-storage:/var/lib/postgresql/data \
    -v ./db/initdb:/docker-entrypoint-initdb.d \
    --network api_default \
    postgres:15

# API起動
dcnt run --rm \
    --name my-api \
    -p 8080:8080 \
    --network api_default
    my-api-img

```

## SpringのAPIのみ
```
$ dimg build -t my-api-img .
$ dimg ls | grep api
my-api-img            latest    7923960f0b55   34 seconds ago   1.18GB
$ dcnt run -p 8080:8080 --rm my-api-img
```

# Docker Composeで実行
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