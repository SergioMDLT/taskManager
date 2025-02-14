# Usa Node.js como base
FROM node:18

# Configura el directorio de trabajo
WORKDIR /app

# Copia solo los archivos necesarios para instalar dependencias
COPY package.json package-lock.json ./

# Instala las dependencias
RUN npm install

# Copia todo el código fuente del proyecto
COPY . .

# Expón el puerto utilizado por ng serve
EXPOSE 4200

# Comando por defecto para ejecutar la aplicación con ng serve
CMD ["npx", "ng", "serve", "--host", "0.0.0.0", "--port", "4200"]
