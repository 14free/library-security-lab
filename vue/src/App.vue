<template>
  <div id="app" style="background-color: #e5f8ef;">
    <div style="height: 60px;line-height: 60px;background-color: #e5f8ef; border: none;margin-bottom: 2px;display: flex;justify-content: space-between;align-items: center;padding: 0 20px;">
      <div style="display: flex;align-items: center;">
        <img src="@/assets/logo.png" alt="" style="width:60px;height:60px;position: relative;top:2px;left:2px">
        <span style="margin-left:20px;font-size: 50px;position: relative;top:-10px">图书管理系统</span>
      </div>
      <div v-if="currentUser" style="display: flex;align-items: center;gap: 15px;">
        <span style="font-size: 16px;">欢迎，{{ currentUser.username }}</span>
        <el-button type="danger" size="small" @click="handleLogout">退出登录</el-button>
      </div>
    </div>
    <div style="display: flex;flex:1;">
      <div style="width:200px;min-height:calc(100vh - 62px);height:200px;overflow: hidden;margin-right: 3px;background-color: #e5f8ef">
        <el-menu :default-active="$route.path" router class="el-menu-demo" >
          <el-menu-item index="/about">
            <span>图书管理</span>
          </el-menu-item>
          <el-menu-item index="/home">
            <span>图书添加</span>
          </el-menu-item>
          <el-menu-item index="/borrow-records">
            <i class="el-icon-document"></i>
            <span>我的借阅</span>
          </el-menu-item>
        </el-menu>
      </div>
      <div style="flex:1;background-color: #e5f8ef">
        <router-view/>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  computed: {
    currentUser() {
      const user = localStorage.getItem('user')
      return user ? JSON.parse(user) : null
    }
  },
  methods: {
    handleLogout() {
      this.$confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        localStorage.removeItem('user')
        this.$message.success('退出成功')
        this.$router.push('/login')
      }).catch(() => {})
    }
  }
}
</script>
