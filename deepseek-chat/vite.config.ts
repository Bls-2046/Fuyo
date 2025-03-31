import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import legacy from '@vitejs/plugin-legacy'

export default defineConfig({
  plugins: [vue(),
    legacy({
      targets: ['defaults', 'not IE 11', 'chrome 50', 'safari 10'], // 兼容目标浏览器
      modernPolyfills: true,              // 为现代浏览器提供 polyfill
      additionalLegacyPolyfills: ['regenerator-runtime/runtime'] // 额外 polyfill
    })],
  optimizeDeps: {
    include: ['marked'],
    exclude: ['vue-demi']
  },
  build: {
    target: 'es2015', // 替代默认的 'esnext'
    commonjsOptions: {
      include: [/marked/, /node_modules/]
    }
  }
})