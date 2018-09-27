<template>
  <div class="container public">
    <div class="row justify-content-center">
      <div class="form">
        <Logo/>
        <form @submit.prevent="submitForm">
          <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
          <div class="form-group">
            <label for="username">{{ $t("loginPage.form.username.label") }}</label>
            <input type="text" class="form-control" id="username" v-model="form.username">
            <div class="field-error" v-if="$v.form.username.$dirty">
              <div class="error" v-if="!$v.form.username.required">{{ $t("loginPage.form.username.required") }}</div>
            </div>
          </div>
          <div class="form-group">
            <label for="password">{{ $t("loginPage.form.password.label") }}</label>
            <input type="password" class="form-control" id="password" v-model="form.password">
            <div class="field-error" v-if="$v.form.password.$dirty">
              <div class="error" v-if="!$v.form.password.required">{{ $t("loginPage.form.password.required") }}</div>
            </div>
          </div>
          <button type="submit" class="btn btn-primary btn-block">{{ $t("loginPage.form.submit") }}</button>
          <div class="links">
            <p class="sign-up text-muted">{{ $t("loginPage.form.noAccountYet") }} <router-link to="register" class="link-sign-up">{{ $t("loginPage.form.signUpHere") }}</router-link></p>
            <p><router-link to="#">{{ $t("loginPage.form.forgotPassword") }}</router-link></p>
          </div>
        </form>
      </div>
    </div>
    <PageFooter/>
  </div>
</template>

<script>
import { required } from 'vuelidate/lib/validators'
import authenticationService from '@/services/authentication'
import Logo from '@/components/Logo.vue'
import PageFooter from '@/components/PageFooter.vue'
import notify from '@/utils/notify'

export default {
  name: 'LoginPage',
  data: function () {
    return {
      form: {
        username: '',
        password: ''
      },
      errorMessage: ''
    }
  },
  components: {
    Logo,
    PageFooter
  },
  validations: {
    form: {
      username: {
        required
      },
      password: {
        required
      }
    }
  },
  methods: {
    submitForm () {
      this.$v.$touch()
      if (this.$v.$invalid) {
        return
      }

      authenticationService.authenticate(this.form).then(() => {
        this.$router.push({name: 'home'})
        this.$bus.$emit('authenticated')
        notify.closeAll()
      }).catch((error) => {
        this.errorMessage = error.message
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.links {
  margin: 30px 0 50px 0;
  text-align: center;
}
</style>
