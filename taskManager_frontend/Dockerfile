# Usa Node.js como base
FROM node:18

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

# Expón el puerto utilizado por ng serve
EXPOSE 4200

# Comando por defecto para ejecutar la aplicación con ng serve
CMD ["ng", "serve", "--host", "0.0.0.0", "--port", "4200"]
