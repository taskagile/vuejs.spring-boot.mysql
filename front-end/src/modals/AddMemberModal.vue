<template>
  <form @submit.prevent="addMember">
    <div class="modal" tabindex="-1" role="dialog" backdrop="static" id="addMemberModal">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Add Member</h5>
            <button type="button" class="close" @click="close" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
            <div class="form-group">
              <input type="text" class="form-control" id="usernameOrEmailAddressInput" v-model="usernameOrEmailAddress" placeholder="Username or email address" maxlength="128">
              <div class="field-error" v-if="$v.usernameOrEmailAddress.$dirty">
                <div class="error" v-if="!$v.usernameOrEmailAddress.required">This is required</div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">Add</button>
            <button type="button" class="btn btn-default btn-cancel" @click="close">Cancel</button>
          </div>
        </div>
      </div>
    </div>
  </form>
</template>

<script>
import $ from 'jquery'
import { required } from 'vuelidate/lib/validators'
import boardService from '@/services/boards'

export default {
  name: 'AddMemberModal',
  props: ['boardId'],
  data () {
    return {
      usernameOrEmailAddress: '',
      errorMessage: ''
    }
  },
  validations: {
    usernameOrEmailAddress: {
      required
    }
  },
  mounted () {
    $('#addMemberModal').on('shown.bs.modal', () => {
      $('#usernameOrEmailAddressInput').trigger('focus')
    })
  },
  methods: {
    addMember () {
      this.$v.$touch()
      if (this.$v.$invalid) {
        return
      }

      boardService.addMember(this.boardId, this.usernameOrEmailAddress).then((member) => {
        this.$emit('added', member)
        this.close()
      }).catch(error => {
        this.errorMessage = error.message
      })
    },
    close () {
      this.$v.$reset()
      this.usernameOrEmailAddress = ''
      this.errorMessage = ''
      $('#addMemberModal').modal('hide')
    }
  }
}
</script>

<style lang="scss" scoped>
.modal {
  .modal-dialog {
    width: 300px;
  }
}
</style>
