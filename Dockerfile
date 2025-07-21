FROM gradle:7

# ビルドコンテキストを/apiに設定
WORKDIR /api
COPY . .

# デフォルトコマンドでAPIを起動
CMD ["./gradlew", "bootRun"]
