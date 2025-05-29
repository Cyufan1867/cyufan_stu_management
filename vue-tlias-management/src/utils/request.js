import axios from 'axios'
import {ElMessage} from "element-plus"
import router from '../router'

const request = axios.create({
    baseURL: '/api',
    timeout: 600000
})

request.interceptors.request.use(
    (config) => {
        let loginUser = JSON.parse(localStorage.getItem('loginUser'))
        console.log(localStorage.getItem('loginUser'))
        if (loginUser) {
            config.headers.token = loginUser.token
        }
        return config
    }
)

request.interceptors.response.use(
    (response) => {
        return response.data
    },
    (error) => {
        if (error.response.status === 401) {
            ElMessage.error('登录已过期，请重新登录')
            router.push('/login')
        } else {
            ElMessage.success('接口访问异常')
        }
        return Promise.reject(error)
    }
)
export default request
