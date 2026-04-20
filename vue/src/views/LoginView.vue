<template>
  <div class="login-container">
    <div class="login-box">
      <h2>图书管理系统 - 登录</h2>
      <el-form :model="loginForm" :rules="rules" ref="loginForm">
        <el-form-item prop="username">
          <el-input 
            v-model="loginForm.username" 
            placeholder="请输入用户名"
            prefix-icon="el-icon-user">
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input 
            v-model="loginForm.password" 
            type="password" 
            placeholder="请输入密码"
            prefix-icon="el-icon-lock"
            @keyup.enter.native="handleLogin">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            style="width: 100%" 
            @click="handleLogin"
            :loading="loading">
            登录
          </el-button>
        </el-form-item>
        <div class="links">
          <router-link to="/register">没有账号？去注册</router-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'LoginView',
  data() {
    return {
      loading: false,
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(async (valid) => {
        if (valid) {
          this.loading = true
          try {
            const res = await axios.post('http://localhost:8080/user/login', this.loginForm, {
              headers: {
                'Content-Type': 'application/json'
              }
            })
            if (res.data.code === '200') {
              localStorage.setItem('user', JSON.stringify(res.data.data))
              this.$message.success('登录成功')
              this.$router.push('/about')
            } else {
              this.$message.error(res.data.msg || '登录失败')
            }
          } catch (error) {
            this.$message.error('登录失败，请检查网络连接')
          } finally {
            this.loading = false
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-box {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
}

.login-box h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}

.links {
  text-align: center;
  margin-top: 10px;
}

.links a {
  color: #409EFF;
  text-decoration: none;
}

.links a:hover {
  text-decoration: underline;
}
</style>
