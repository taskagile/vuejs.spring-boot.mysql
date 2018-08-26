<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style lang="scss">
html {
  font-size: 14px;
}
.form-group {
  .error {
    line-height: 1;
    display: none;
    margin-top: 5px;
  }
}
.field-error {
  .error {
    display: block;
    color: #ff0000;
  }
}
</style>
