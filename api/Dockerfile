# マルチステージビルド(開発用)
FROM gradle:7-jdk17 AS base

# ビルドコンテキストをコンテナ上の/apiに配置
# WORKDIR /api
WORKDIR /workspace

# デフォルトコマンドでAPIを起動
# 本コマンドはdocker-compose.ymlのvolumes後に実行される
CMD ["./gradlew", "bootRun"]

# マルチステージビルド(テスト・ビルド用)
FROM base AS test-and-build
WORKDIR /workspace
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build

# マルチステージビルド(本番リリース用)
FROM eclipse-temurin:17.0.15_6-jre-noble AS prd
COPY --from=test-and-build /workspace/build/libs/*.jar ./api.jar
CMD ["java", "-jar", "api.jar"]