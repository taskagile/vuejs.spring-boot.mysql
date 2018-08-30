import Vue from 'vue'
import VueI18n from 'vue-i18n'
import { enUS, zhCN } from './locale'

Vue.use(VueI18n)

// Create VueI18n instance with options
export const i18n = new VueI18n({
  locale: 'en_US',
  messages: {
    'en_US': enUS,
    'zh_CN': zhCN
  }
})
