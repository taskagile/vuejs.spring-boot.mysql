<template>
  <div class="logo-wrapper">
    <img class="logo" src="/images/logo.png">
    <div class="tagline">{{ $t("logo.tagLine") }}</div>
  </div>
</template>

<script>
export default {
  name: 'Logo'
}
</script>

<style lang="scss" scoped>
.logo-wrapper {
  text-align: center;
  margin-bottom: 40px;

  .tagline {
    line-height: 180%;
    color: #666;
  }

  .logo {
    max-width: 150px;
    margin: 0 auto;
  }
}
</style>
