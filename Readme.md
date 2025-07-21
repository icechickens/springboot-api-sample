# Dockerで実行
## http://localhost:8080/api/hello?lang=ja でローカルブラウザからアクセス可能
```
$ dimg build -t my-api-img .
$ dimg ls | grep api
my-api-img            latest    7923960f0b55   34 seconds ago   1.18GB
$ dcnt run -p 8080:8080 --rm my-api-img
```

