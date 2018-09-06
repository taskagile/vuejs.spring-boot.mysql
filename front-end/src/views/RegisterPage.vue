<template>
  <div class="container public">
    <div class="row justify-content-center">
      <div class="form">
        <Logo/>
        <form @submit.prevent="submitForm">
          <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
          <div class="form-group">
            <label for="username">{{ $t('registerPage.form.username.label') }}</label>
            <input type="text" class="form-control" id="username" v-model="form.username">
            <div class="field-error" v-if="$v.form.username.$dirty">
              <div class="error" v-if="!$v.form.username.required">{{ $t('registerPage.form.username.required') }}</div>
              <div class="error" v-if="!$v.form.username.alphaNum">{{ $t('registerPage.form.username.alphaNum') }}</div>
              <div class="error" v-if="!$v.form.username.minLength">{{ $t('registerPage.form.username.minLength', {minLength: $v.form.username.$params.minLength.min}) }}</div>
              <div class="error" v-if="!$v.form.username.maxLength">{{ $t('registerPage.form.username.maxLength', {maxLength: $v.form.username.$params.maxLength.max}) }}</div>
            </div>
          </div>
          <div class="form-group">
            <label for="emailAddress">{{ $t('registerPage.form.emailAddress.label') }}</label>
            <input type="email" class="form-control" id="emailAddress" v-model="form.emailAddress">
            <div class="field-error" v-if="$v.form.emailAddress.$dirty">
              <div class="error" v-if="!$v.form.emailAddress.required">{{ $t('registerPage.form.emailAddress.required') }}</div>
              <div class="error" v-if="!$v.form.emailAddress.email">{{ $t('registerPage.form.emailAddress.email') }}</div>
              <div class="error" v-if="!$v.form.emailAddress.maxLength">{{ $t('registerPage.form.emailAddress.maxLength', {maxLength: $v.form.emailAddress.$params.maxLength.max}) }}</div>
            </div>
          </div>
          <div class="form-group">
            <label for="firstName">{{ $t('registerPage.form.firstName.label') }}</label>
            <input type="text" class="form-control" id="firstName" v-model="form.firstName">
            <div class="field-error" v-if="$v.form.firstName.$dirty">
              <div class="error" v-if="!$v.form.firstName.required">{{ $t('registerPage.form.firstName.required') }}</div>
              <div class="error" v-if="!$v.form.firstName.alpha">{{ $t('registerPage.form.firstName.alpha') }}</div>
              <div class="error" v-if="!$v.form.firstName.minLength">{{ $t('registerPage.form.firstName.minLength', {minLength: $v.form.firstName.$params.minLength.min}) }}</div>
              <div class="error" v-if="!$v.form.firstName.maxLength">{{ $t('registerPage.form.firstName.maxLength', {maxLength: $v.form.firstName.$params.maxLength.max}) }}</div>
            </div>
          </div>
          <div class="form-group">
            <label for="lastName">{{ $t('registerPage.form.lastName.label') }}</label>
            <input type="text" class="form-control" id="lastName" v-model="form.lastName">
            <div class="field-error" v-if="$v.form.lastName.$dirty">
              <div class="error" v-if="!$v.form.lastName.required">{{ $t('registerPage.form.lastName.required') }}</div>
              <div class="error" v-if="!$v.form.lastName.alpha">{{ $t('registerPage.form.lastName.alpha') }}</div>
              <div class="error" v-if="!$v.form.lastName.minLength">{{ $t('registerPage.form.lastName.minLength', {minLength: $v.form.lastName.$params.minLength.min}) }}</div>
              <div class="error" v-if="!$v.form.lastName.maxLength">{{ $t('registerPage.form.lastName.maxLength', {maxLength: $v.form.lastName.$params.maxLength.max}) }}</div>
            </div>
          </div>
          <div class="form-group">
            <label for="password">{{ $t('registerPage.form.password.label') }}</label>
            <input type="password" class="form-control" id="password" v-model="form.password">
            <div class="field-error" v-if="$v.form.password.$dirty">
              <div class="error" v-if="!$v.form.password.required">{{ $t('registerPage.form.password.required') }}</div>
              <div class="error" v-if="!$v.form.password.minLength">{{ $t('registerPage.form.password.minLength', {minLength: $v.form.password.$params.minLength.min}) }}</div>
              <div class="error" v-if="!$v.form.password.maxLength">{{ $t('registerPage.form.password.maxLength', {maxLength: $v.form.password.$params.maxLength.max}) }}</div>
            </div>
          </div>
          <button type="submit" class="btn btn-primary btn-block">{{ $t('registerPage.form.submit') }}</button>
          <p class="accept-terms text-muted">
            <i18n path="registerPage.form.terms.accept" tag="p" class="accept-terms text-muted">
              <a place="termsOfService" href="#">{{ $t('registerPage.form.terms.termsOfService') }}</a>
              <a place="privacyPolicy" href="#">{{ $t('registerPage.form.terms.privacyPolicy') }}</a>
            </i18n>
          </p>
          <p class="text-center text-muted">{{ $t('registerPage.form.alreadyHaveAccount') }} <router-link to="login">{{ $t("registerPage.form.signIn") }}</router-link></p>
        </form>
      </div>
    </div>
    <PageFooter/>
  </div>
</template>

<script>
import { required, email, minLength, maxLength, alphaNum, alpha } from 'vuelidate/lib/validators'
import registrationService from '@/services/registration'
import Logo from '@/components/Logo.vue'
import PageFooter from '@/components/PageFooter.vue'

export default {
  name: 'RegisterPage',
  data: function () {
    return {
      form: {
        username: '',
        emailAddress: '',
        firstName: '',
        lastName: '',
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
        required,
        minLength: minLength(2),
        maxLength: maxLength(50),
        alphaNum
      },
      emailAddress: {
        required,
        email,
        maxLength: maxLength(100)
      },
      firstName: {
        required,
        minLength: minLength(1),
        maxLength: maxLength(45),
        alpha
      },
      lastName: {
        required,
        minLength: minLength(1),
        maxLength: maxLength(45),
        alpha
      },
      password: {
        required,
        minLength: minLength(6),
        maxLength: maxLength(30)
      }
    }
  },
  methods: {
    submitForm () {
      this.$v.$touch()
      if (this.$v.$invalid) {
        return
      }

      registrationService.register(this.form).then(() => {
        this.$router.push({name: 'login'})
      }).catch((error) => {
        this.errorMessage = 'Failed to register user. ' + error.message
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.accept-terms {
  margin: 20px 0 40px 0;
}
</style>
