FROM node:18 AS test-and-build

# ビルドコンテキストをコンテナ上の/web配置
# WORKDIR /web
WORKDIR /workspace
COPY package*.json ./
RUN npm install
COPY . .

# Docker Composeは本番ビルド時に使用しないため
ARG REACT_APP_API_SERVER
RUN npm run test && npm run build

# デフォルトコマンドでWEBを起動
# 本コマンドはdocker-compose.ymlのvolumes後に実行される
CMD [ "npm", "run", "start" ]

# マルチステージビルド
# 本番用のイメージを作成
FROM nginx:latest AS prd
COPY --from=test-and-build /workspace/build /var/www
COPY nginx.conf /etc/nginx/conf.d/default.conf