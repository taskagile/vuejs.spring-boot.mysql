<template>
  <div>
    <PageHeader />
    <div class="boards-container">
      <div class="boards-section">
        <h2 class="section-title">Personal Boards</h2>
        <div class="boards d-flex align-content-start flex-wrap">
          <div class="board list-inline-item">
            <h3>vuejs.spring-boot.mysql</h3>
            <p>
              An implementation of TaskAgile application with Vue.js, Spring Boot, and MySQL
            </p>
          </div>
          <div class="board add list-inline-item">
            <font-awesome-icon icon="plus" />
            <div>Create New Board</div>
          </div>
        </div>
      </div>
      <div class="create-team-wrapper">
        <button class="btn btn-link">+ Create New Team</button>
      </div>
    </div>
  </div>
</template>

<script>
import PageHeader from '@/components/PageHeader.vue'
export default {
  name: 'HomePage',
  components: {
    PageHeader
  }
}
</script>

<style lang="scss" scoped>
.boards-container {
  padding: 0 35px;

  h2 {
    font-size: 18px;
    margin-bottom: 15px;
    font-weight: 400;
  }

  .boards-section {
    margin: 30px 10px;

    .boards {

      .board {
        width: 270px;
        height: 110px;
        border-radius: 5px;
        background-color: #377EF6;
        color: #fff;
        padding: 15px;
        margin-right: 10px;
        cursor: pointer;

        h3 {
          font-size: 16px;
        }

        p {
          line-height: 1.2;
          font-size: 90%;
          font-weight: 100;
          color: rgba(255, 255, 255, 0.70)
        }
      }

      .add {
        background-color: #f4f4f4;
        color: #666;
        text-align: center;
        padding-top: 30px;
        font-weight: 400;
      }
    }
  }

  .create-team-wrapper {
    .btn-link {
      color: #666;
      text-decoration: underline;
    }
  }
}
</style>
