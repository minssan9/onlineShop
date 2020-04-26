<template>
  <div>
    <div class="form-group">
      <label for="exampleFormControlTextarea1">Review</label>
      <textarea v-model="content" class="form-control" id="exampleFormControlTextarea1" rows="3"
                placeholder="리뷰를 남겨주세요."></textarea>
    </div>
    <div class="custom-file mt-2">
      <input id="file" name="file" type="file" ref="files" @change="onFileList" class="custom-file-input">
      <label class="custom-file-label">Choose file</label>
    </div>
    <ul class="list-group mt-2">
      <li v-for="file in files" class="list-group-item">{{file.name}}</li>
    </ul>
    <div class="row">
      <div class="form-group col-5">
        <select v-model="likeStatus" class="form-control mt-3" id="exampleFormControlSelect1">
          <option v-for="(option,index) in options" :key="index">{{option}}</option>
        </select>
      </div>
    </div>

    <div class="text-right">
      <button class="btn btn-primary" @click="onAddReview">리뷰등록</button>
    </div>
  </div>
</template>

<script>

  import {mapActions} from 'vuex'

  export default {
    data() {
      return {
        options:['아주 좋아요','좋아요','보통 이예요','그저 그래요','별로예요'],
        content: '',
        likeStatus: '아주 좋아요',
        files:''
      }
    },
    methods: {
      ...mapActions([
        'ADD_REVIEW_FILE'
      ]),
      onAddReview() {
        let formData = new FormData()
        let inputFile = document.querySelector('#file')
        let file = inputFile.files
        for (let i = 0; i < file.length; i++) {
          if (!this.isExtensionAndSize(file[i].name, file[i].size)) {
            return
          }
          formData.append('uploadFile', file[i])
        }

        // 파일업로드 -> 파일업로드결과 응답-> 아이템정보에 파일업로드결과 추가해서 다시요청

        this.ADD_REVIEW_FILE({
          item:{id:this.$route.params.id},
          formData: formData,
          review: {content: this.content, likeStatus: this.likeStatus, reviewFile: ''}
        })

        alert('등록이 완료되었습니다.')
        this.$router.push('/')
      },
      isExtensionAndSize(fileName, fileSize) {
        let regex = new RegExp('(.*?)\.(exe|sh|zip|alz)$')
        let maxSize = 1048576  // 1MB

        if (fileSize >= maxSize) {
          alert('파일 사이즈 초과')
          return false
        }

        if (regex.test(fileName)) {
          alert('해당 파일형식은 업로드 불가능합니다.')
          return false
        }

        return true
      },
      onFileList() {
        this.files = this.$refs.files.files;
      }
    }
  }
</script>

<style scoped>

</style>
