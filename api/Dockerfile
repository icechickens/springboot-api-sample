FROM gradle:7

# ビルドコンテキストをコンテナ上の/apiに配置
# WORKDIR /api
WORKDIR /workspace

# デフォルトコマンドでAPIを起動
# 本コマンドはdocker-compose.ymlのvolumes後に実行される
CMD ["./gradlew", "bootRun"]
