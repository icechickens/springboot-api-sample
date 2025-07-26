FROM node:18

# ビルドコンテキストをコンテナ上の/web配置
# WORKDIR /web
WORKDIR /workspace
COPY package*.json ./
RUN npm install

# デフォルトコマンドでWEBを起動
# 本コマンドはdocker-compose.ymlのvolumes後に実行される
CMD [ "npm", "run", "start" ]