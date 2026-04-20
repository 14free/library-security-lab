<template>
  <div class="register-container">
    <div class="register-box">
      <h2>图书管理系统 - 注册</h2>
      <el-form :model="registerForm" :rules="rules" ref="registerForm">
        <el-form-item prop="username">
          <el-input 
            v-model="registerForm.username" 
            placeholder="请输入用户名"
            prefix-icon="el-icon-user">
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input 
            v-model="registerForm.password" 
            type="password" 
            placeholder="请输入密码"
            prefix-icon="el-icon-lock">
          </el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input 
            v-model="registerForm.confirmPassword" 
            type="password" 
            placeholder="请确认密码"
            prefix-icon="el-icon-lock">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            style="width: 100%" 
            @click="handleRegister"
            :loading="loading">
            注册
          </el-button>
        </el-form-item>
        <div class="links">
          <router-link to="/login">已有账号？去登录</router-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'RegisterView',
  data() {
    const validatePass = (rule, value, callback) => {
      if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback()
      }
    }
    
    return {
      loading: false,
      registerForm: {
        username: '',
        password: '',
        confirmPassword: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码至少6位', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleRegister() {
      this.$refs.registerForm.validate(async (valid) => {
        if (valid) {
          this.loading = true
          try {
            const { confirmPassword, ...userData } = this.registerForm
            const res = await axios.post('http://localhost:8080/user/register', userData, {
              headers: {
                'Content-Type': 'application/json'
              }
            })
            if (res.data.code === '200') {
              this.$message.success('注册成功')
              this.$router.push('/login')
            } else {
              this.$message.error(res.data.msg || '注册失败')
            }
          } catch (error) {
            this.$message.error('注册失败，请检查网络连接')
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
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px 0;
}

.register-box {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
}

.register-box h2 {
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
