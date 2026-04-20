import axios from "axios";

const request=axios.create({
    baseURL:'http://localhost:8080',
    timeout:5000,
})

request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    const user = localStorage.getItem('user')
    if (user) {
        const userData = JSON.parse(user)
        config.headers['Authorization'] = userData.id ? `Bearer ${userData.id}` : ''
    }
    return config
},error => {
    return Promise.reject(error)
});

request.interceptors.response.use(
    response =>{
        let res = response.data;
        if(typeof res ==='string'){
            res=res? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('err'+error)
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('user')
            window.location.href = '/login'
        }
        return Promise.reject(error)
    }
)

export default request
