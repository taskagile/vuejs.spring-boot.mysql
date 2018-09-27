import _ from 'lodash'
import { i18n } from '@/i18n'
import eventBus from '@/event-bus'

export default {
  parse (error) {
    if (error.response) {
      const status = error.response.status
      const data = error.response.data
      if (status === 400) {
        if (data && data.message) {
          return new Error(data.message)
        } else {
          return new Error(i18n.t('error.request.bad'))
        }
      } else if (status === 401) {
        eventBus.$emit('user.unauthenticated')
        return new Error(i18n.t('error.request.notAuthorized'))
      } else if (status === 403) {
        return new Error(i18n.t('error.request.forbidden'))
      } else if (status === 404) {
        return new Error(i18n.t('error.request.notFound'))
      } else if (status === 500) {
        if (data && data.message) {
          return new Error(data.message)
        } else {
          return new Error(i18n.t('error.request.unknownServerError'))
        }
      } else {
        return new Error(i18n.t('error.request.failed'))
      }
    } else if (error.request) {
      // Request was made and no response
      return new Error(i18n.t('error.request.noResponse'))
    } else {
      return _.isError(error) ? error : new Error(error)
    }
  }
}
