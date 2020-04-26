<template>
  <div>
    <h3> My Page</h3>
    <hr/>
    <div class="row">
      <div @click="isShowImgChangeModal=true" class="btn col-xl-4 col-3">
        <img v-if="account.accountFile" class="img-thumbnail"  :src="`http://localhost:8090/api/accounts/download?fileName=${account.accountFile.uuid}_${account.accountFile.fileName}`"/>
        <img v-else class="img-thumbnail" src="../../../assets/person_profile.png">
      </div>
      <div class="col-xl-3 col-2">
        <div class="row ml-1 mt-2">
          <p><strong> {{account.name}}</strong>님</p>
          <router-link to="/myPage/setting" class="ml-1 btn btn-sm btn-outline-danger">수정</router-link>
        </div>
      </div>
      <div class="col-xl-3 col-4 mt-2">
        <strong>주소</strong>
        <p> {{account.address}}</p>
        <strong>연락처</strong>
        <p>{{account.phone}}</p>
      </div>
      <div class="col-xl-2 col-3 mt-2">
       <strong>포인트</strong>
        <p>{{account.point}}점</p>
        <strong>Level</strong>
        <p><strong>{{account.level}}</strong></p>
      </div>
    </div>

    <div class="row"></div>
    <span class="ml-3 text-left" style="font-size:2vw;">프로필 클릭시 변경</span>

    <Modal v-if="isShowImgChangeModal">
      <h3 slot="header">이미지 업로드</h3>
      <div slot="body">
        <div class="form-group">
          <img v-if="url" class="img-thumbnail" :src="url" />
          <img v-else-if="account.accountFile" class="img-thumbnail" :src="`http://localhost:8090/api/accounts/download?fileName=${account.accountFile.uuid}_${account.accountFile.fileName}`"/>
          <img v-else class="img-thumbnail" src="../../../assets/person_profile.png">
          <div class="custom-file mt-2">
            <input id="file" name="file" type="file" ref="files" @change="onFileChange" class="custom-file-input">
            <label class="custom-file-label">Choose file</label>
          </div>
          <ul class="list-group mt-2">
            <li v-for="file in files" class="list-group-item">{{file.name}}</li>
          </ul>
        </div>
      </div>
      <span slot="footer">
        <button @click="onAddItem" class="btn btn-outline-warning">수정</button>
        <button @click="onFileList" class="btn btn-outline-warning">취소</button>
      </span>
    </Modal>
  </div>
</template>

<script>

  import Modal from '../Modal'
  import {mapActions} from 'vuex'

  export default {
    props: ['account'],

    data() {
      return {
        files: '',
        isShowImgChangeModal: false,
        url:''
      }
    },
    components: {
      Modal
    },
    methods: {
      ...mapActions([
        'ADD_ACCOUNT_FILE'
      ]),
      onAddItem() {
        let formData = new FormData()
        let inputFile = document.querySelector('#file')
        let file = inputFile.files


        for (let i = 0; i < file.length; i++) {
          if (!this.isExtensionAndSize(file[i].name, file[i].size)) {
            return
          }
          formData.append('uploadFile', file[i])
        }

        this.ADD_ACCOUNT_FILE({formData: formData, accountId: this.account.id})
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
        this.isShowImgChangeModal=false
        this.url=''
      },
      onFileChange(e){
        const file = e.target.files[0];
        this.url = URL.createObjectURL(file);
        this.files = this.$refs.files.files;
      }
    }
  }
</script>

<style scoped>

</style>


