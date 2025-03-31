import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  optimizeDeps: {
    include: ['marked'],
    exclude: ['vue-demi']
  },
  build: {
    commonjsOptions: {
      include: [/marked/, /node_modules/]
    }
  }
})