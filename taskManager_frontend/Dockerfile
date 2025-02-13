# Etapa 1: Construcción de la aplicación Angular
FROM node:18 AS build

# Configura el directorio de trabajo
WORKDIR /app

# Instala Angular CLI globalmente
RUN npm install -g @angular/cli

# Copia los archivos necesarios para instalar dependencias
COPY package*.json ./

# Instala las dependencias del proyecto
RUN npm install

# Copia todo el código fuente del proyecto
COPY . .

# Construye la aplicación especificando el nombre del proyecto
RUN ng build taskManager_frontend --configuration production

# Etapa 2: Servir los archivos con Nginx
FROM nginx:alpine

# Copia los archivos generados en la build al directorio de Nginx
COPY --from=build /app/dist/task-manager-frontend /usr/share/nginx/html

# Expón el puerto que Nginx usará para servir la aplicación
EXPOSE 80

# Comando para iniciar Nginx
CMD ["nginx", "-g", "daemon off;"]
