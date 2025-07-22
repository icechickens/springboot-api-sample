FROM gradle:7

# ビルドコンテキストをコンテナ上の/apiに配置
WORKDIR /api
COPY . .

# デフォルトコマンドでAPIを起動
CMD ["./gradlew", "bootRun"]
