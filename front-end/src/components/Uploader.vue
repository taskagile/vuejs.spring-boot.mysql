<template>
  <div class="fileinput-button">
    <font-awesome-icon :icon="icon"  class="icon" v-if="icon"/> {{ label }}
    <input :id="id" type="file" name="file" multiple>
  </div>
</template>

<script>
import $ from 'jquery'
import 'jquery-ui/ui/widget'
import 'blueimp-file-upload/js/jquery.fileupload'
import 'blueimp-file-upload/js/jquery.iframe-transport'

export default {
  name: 'Uploader',
  props: ['id', 'url', 'icon', 'label'],
  watch: {
    url () {
      if (!this.url) {
        return
      }

      $('#' + this.id).fileupload({
        url: this.url,
        dataType: 'json',
        add: (e, data) => {
          this.$emit('uploading', data.files[0])
          data.submit()
        },
        fail: (e, data) => {
          this.$emit('failed', data._response.jqXHR.responseJSON.message)
        },
        done: (e, data) => {
          this.$emit('uploaded', data.result)
        },
        progress: (e, data) => {
          let progress = parseInt(data.loaded / data.total * 100, 10)
          this.$emit('progress', progress)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.icon {
  margin-right: .5rem;
}
</style>
