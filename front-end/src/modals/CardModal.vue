<template>
  <div class="modal" tabindex="-1" role="dialog" backdrop="static" id="cardModal">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <font-awesome-icon icon="window-maximize" class="card-title-icon" />
          <h4 class="modal-title">
            <textarea id="cardTitle" class="auto-size" v-model="title" @keydown.enter.prevent="changeCardTitle"></textarea>
            <div class="meta-card-list">in list {{ cardList.name }}</div>
          </h4>
          <button type="button" class="close" @click="close" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <div class="card-container">
            <div class="wrapper assignee-wrapper" v-show="assignees.length">
              <div class="wrapper-body"></div>
            </div>
            <div class="wrapper description-wrapper">
              <div class="wrapper-body">
                <div class="empty-tip" v-show="!editingDescription" @click="showEditDescription">
                  Description <font-awesome-icon icon="pencil-alt" />
                </div>
                <form class="description-form" @submit.prevent="changeCardDescription" v-show="editingDescription">
                  <div class="form-group">
                    <textarea id="cardDescription" class="auto-size" v-model="description"></textarea>
                  </div>
                  <button type="submit" class="btn btn-primary">Save</button>
                  <span class="btn btn-link btn-cancel" @click="cancelEditDescription">Cancel</span>
                  <span class="format-support float-right">Support Markdown</span>
                </form>
                <div class="description" v-show="description && !editingDescription" v-html="descriptionHtml"></div>
              </div>
            </div>
            <div class="wrapper attachments-wrapper" v-show="attachments.length || uploadingCount">
              <h5><font-awesome-icon icon="paperclip" class="icon"/> <span>Attachments</span></h5>
              <div class="wrapper-body">
                <div class="uploading" v-show="uploadingCount"><font-awesome-icon icon="spinner" class="fa-spin" spin/>Uploading...</div>
                <ul class="list-unstyled">
                  <li class="media" v-for="attachment in cardAttachments" v-bind:key="attachment.id">
                    <div class="mr-3">
                      <div class="preview thumbnail" v-if="attachment.previewUrl">
                        <img :src="attachment.previewUrl" />
                      </div>
                      <div class="preview file-type" v-if="!attachment.previewUrl">
                        {{ attachment.fileType }}
                      </div>
                    </div>
                    <div class="media-body">
                      <h6 class="mt-0 mb-1"><a :href="attachment.fileUrl" target="_blank">{{ attachment.fileName }}</a></h6>
                      <p class="when">Added {{ when(attachment.createdDate) }} ago</p>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
            <div class="wrapper comment-form-wrapper">
              <h5><font-awesome-icon icon="comment" class="icon"/> <span>Add Comment</span></h5>
              <div class="wrapper-body">
                <form class="comment-form" @submit.prevent="addComment">
                  <div class="form-group">
                    <textarea v-model="newComment" placeholder="Add a comment here"></textarea>
                  </div>
                  <button type="submit" class="btn btn-primary" :disabled="!newComment">Save</button>
                </form>
              </div>
            </div>
            <div class="wrapper activities-wrapper">
              <h5>
                <font-awesome-icon icon="list-ul" class="icon"/>
                <span>Activities</span>
              </h5>
              <div class="wrapper-body">
                <div class="activity" v-for="activity in cardActivities" v-bind:key="activity.id">
                  <div><strong>{{ activity.user.name }}</strong> <span class="when">{{ activity.when }} ago</span></div>
                  <div class="detail" :class="activity.type">{{ activity.actionDetail }}</div>
                </div>
              </div>
            </div>
          </div>
          <div class="card-controls">
            <h5>Add to Card</h5>
            <div class="control"><font-awesome-icon icon="user" class="icon" /> Members</div>
            <div class="control">
              <uploader
                id="cardAttachment"
                :url="attachmentUploadUrl"
                icon="paperclip"
                label="Attachment"
                @uploading="onUploadingAttachment"
                @progress="onUploadingProgressUpdated"
                @failed="onAttachmentUploadFailed"
                @uploaded="onAttachmentUploaded"/>
            </div>
            <h5 class="actions">Actions</h5>
            <div class="control"><font-awesome-icon icon="archive"  class="icon" /> Archive</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
import { formatDistance } from 'date-fns'
import autosize from 'autosize'
import showdown from 'showdown'
import notify from '@/utils/notify'
import cardService from '@/services/cards'
import Uploader from '@/components/Uploader'

showdown.setOption('strikethrough', true)
showdown.setOption('tables', true)
const markdownConverter = new showdown.Converter()

export default {
  name: 'CardModal',
  props: ['card', 'cardList', 'board', 'members'],
  data () {
    return {
      cardId: 0,
      title: '',
      description: '',
      assignees: [],
      attachments: [],
      activities: [],
      newComment: '',
      editingDescription: false,
      detailHidden: false,
      uploadingCount: 0
    }
  },
  computed: {
    descriptionHtml () {
      if (!this.description) {
        return ''
      }
      return markdownConverter.makeHtml(this.description)
    },
    cardActivities () {
      if (!this.members.length || !this.activities.length) {
        return []
      }

      const userById = {}
      this.members.forEach(member => {
        userById[member.id] = member
      })
      const cardActivities = []
      const now = new Date()
      this.activities.forEach(activity => {
        const detail = JSON.parse(activity.detail)
        let actionDetail = ''
        if (activity.type === 'add-comment') {
          actionDetail = detail.comment
        } else if (activity.type === 'add-card') {
          actionDetail = 'Added this card'
        } else if (activity.type === 'add-attachment') {
          actionDetail = 'Added attachment ' + detail.fileName
        } else if (activity.type === 'change-card-description') {
          actionDetail = 'Changed card description'
        } else if (activity.type === 'change-card-title') {
          actionDetail = 'Changed card title'
        }

        cardActivities.push({
          user: userById[activity.userId],
          type: activity.type,
          actionDetail: actionDetail,
          when: formatDistance(new Date(activity.createdDate), now),
          createdDate: activity.createdDate
        })
      })
      cardActivities.sort((a1, a2) => {
        return a2.createdDate - a1.createdDate
      })
      return cardActivities
    },
    cardAttachments () {
      const cardAttachments = []
      this.attachments.forEach(attachment => {
        cardAttachments.push(attachment)
      })
      return cardAttachments.sort((a, b) => {
        return b.createdDate - a.createdDate
      })
    },
    attachmentUploadUrl () {
      return this.card.id ? '/api/cards/' + this.card.id + '/attachments' : ''
    }
  },
  components: {
    Uploader
  },
  watch: {
    card () {
      this.cardId = this.card.id
      this.title = this.card.title
      this.description = this.card.description
    }
  },
  mounted () {
    setTimeout(() => {
      autosize($('.auto-size'))
    }, 0)

    $('#cardModal').on('show.bs.modal', () => {
      setTimeout(() => {
        autosize.update($('.auto-size'))
      }, 0)

      this.loadActivities()
      this.loadAttachments()
    })
  },
  methods: {
    changeCardTitle () {
      cardService.changeCardTitle(this.cardId, this.title).then(() => {
        this.$emit('titleChanged', {cardId: this.cardId, title: this.title})
        $('#cardModal').focus()
      }).catch(error => {
        notify.error(error.message)
      })
    },
    changeCardDescription () {
      cardService.changeCardDescription(this.cardId, this.description).then(() => {
        this.$emit('descriptionChanged', {cardId: this.cardId, description: this.description})
        this.editingDescription = false
      }).catch(error => {
        notify.error(error.message)
      })
    },
    showEditDescription () {
      this.editingDescription = true
      this.$nextTick(() => {
        $('#cardDescription').focus()
        autosize.update($('.auto-size'))
      })
    },
    cancelEditDescription () {
      this.editingDescription = false
    },
    addComment () {
      cardService.addCardComment(this.cardId, this.newComment).then(commentActivity => {
        this.newComment = ''
        this.activities.push(commentActivity)
      }).catch(error => {
        notify.error(error.message)
      })
    },
    loadActivities () {
      cardService.getCardActivities(this.cardId).then(({activities}) => {
        this.activities = activities
      }).catch(error => {
        notify.error(error.message)
      })
    },
    loadAttachments () {
      cardService.getCardAttachments(this.cardId).then(({attachments}) => {
        this.attachments = attachments
      }).catch(error => {
        notify.error(error.message)
      })
    },
    onUploadingAttachment () {
      this.uploadingCount++
    },
    onUploadingProgressUpdated (progress) {
      console.log('Uploading progress: ' + progress + '%')
    },
    onAttachmentUploadFailed (error) {
      this.uploadingCount--
      notify.error(error)
    },
    onAttachmentUploaded (attachment) {
      this.uploadingCount--
      this.attachments.push(attachment)
      if (!this.card.coverImage && attachment.previewUrl) {
        this.$emit('coverImageChanged', {
          cardId: this.card.id,
          cardListId: this.cardList.id,
          coverImage: attachment.previewUrl
        })
      }
    },
    when (createdDate) {
      return formatDistance(new Date(createdDate), new Date())
    },
    close () {
      $('#cardModal').modal('hide')
    }
  }
}
</script>

<style lang="scss" scoped>
.modal {
  .modal-dialog {
    width: 800px;
    max-width: 800px;
    top: 55px;
    margin-bottom: 80px;

    .modal-header {
      padding: 1.5rem 1.5rem .5rem;
      position: relative;

      .card-title-icon {
        position: absolute;
        font-size: 1rem;
        top: 2em;
        left: 1.5rem;
        color: #666;
      }

      .modal-title {
        font-size: 1.4rem;
        line-height: 1.2;
        margin-left: 1.5rem;
        width: 100%;

        textarea {
          width: 100%;
          vertical-align: top;
          resize: none;
          padding: 2px 5px;
          border: 1px solid transparent;
          outline: 1px solid transparent;
          line-height: 24px;
          height: 24px;
          min-height: 30px;
        }

        textarea:focus {
          border: 1px solid #377EF6;
          border-radius: 5px;
        }

        .meta-card-list {
          font-size: .9rem;
          font-weight: 400;
          line-height: 1.5;
          margin-top: .5rem;
          margin-left: .5rem;
        }
      }

      .close {
        font-size: 2rem;
        font-weight: 400;
        padding: .5rem 1rem 0 1rem;
      }
    }

    .modal-body {
      padding: .5rem 1.5rem 5rem;
      display: flex;

      .card-container {
        flex-grow: 1;

        textarea {
          width: 100%;
          padding: 5px 10px;
          border-radius: 3px;
          outline: 1px solid transparent;
          border: 1px solid #ddd;
        }

        textarea:focus {
          border: 1px solid #377EF6;
        }

        .wrapper {
          max-width: 555px;
          margin: 2rem 0;

          h5 {
            font-size: 1.2rem;
            margin-bottom: 1rem;

            .icon {
              color: #666;
            }

            span {
              margin-left: 1rem;
            }
          }

          .wrapper-body {
            padding-left: 2rem;
          }

          .form-group {
            margin: 0;
          }
        }

        .description-wrapper {
          margin: 0 0 3.5rem 0;

          .empty-tip {
            color: #666;
            cursor: pointer;
          }

          .empty-tip:hover {
            color: #000;
          }

          .description {
            margin-top: .5rem;
          }
        }

        .description-form {
          .format-support {
            line-height: 30px;
            font-size: .9rem;
            color: #666;
          }
        }

        .attachments-wrapper {

          .uploading {
            text-align: center;
            border: 1px solid #eeeeee;
            margin-bottom: 1rem;
            padding: .5rem 1rem;
            border-radius: 5px;

            .fa-spin {
              animation: fa-spin .5s infinite linear;
              margin-right: 5px;
            }
          }

          .media {
            margin-bottom: .5rem;

            h6 {
              word-break: break-all;
              font-weight: 400;
              font-size: 1rem;

              a {
                color: #333;
              }
            }

            .preview {
              width: 145px;
              height: 90px;
              background: #e4e4e4;

              img {
                max-width: 145px;
                max-height: 90px;
              }
            }

            .preview.file-type {
              text-align: center;
              border: 1px solid #eee;
              line-height: 90px;
              font-size: 1.5rem;
              overflow: hidden;
            }

            .when {
              color: #999;
            }
          }
        }

        .activities-wrapper {
          .details-toggle {
            font-weight: 400;
            font-size: .9rem;
            cursor: pointer;
            color: #999;
            text-decoration: underline;
          }

          .details-toggle:hover {
            color: #000;
          }

          .activity {
            margin-bottom: 1rem;
            border-bottom: 1px solid #eee;

            .when {
              margin-left: .5rem;
              color: #999;
            }

            .detail {
              border: 1px solid transparent;
              display: inline-block;
              padding: .2rem 0;
              margin-bottom: .5rem;
              color: #666;
            }

            .detail.add-comment {
              color: #000;
              padding: 2px 5px;
              border: 1px solid #eee;
              background: #f4f4f4;
              border-radius: 3px;
              margin-top: .2rem;
            }
          }
        }
      }

      .card-controls {
        width: 180px;
        margin-left: 1.5rem;

        h5 {
          font-size: 1rem;
        }

        h5.actions {
          margin-top: 2rem;
        }

        .control {
          position: relative;
          overflow: hidden;
          padding: .2rem .8rem;
          height: 30px;
          border: 1px solid #eee;
          background-color: #f4f4f4;
          border-radius: 3px;
          cursor: pointer;
          margin-bottom: .5rem;
          font-weight: 600;

          .icon {
            margin-right: .5rem;
            color: #666;
            font-weight: 300;
          }
        }

        .control:hover {
          background-color: #ddd;
          border: 1px solid #ccc;
        }
      }
    }
  }
}
</style>
