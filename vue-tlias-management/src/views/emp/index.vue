<script setup>
//声明token
const token = ref('')
import {ref, watch, onMounted} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {addApi, queryPageApi, queryInfoApi, updateApi, deleteApi} from '@/api/emp'
import {Plus} from "@element-plus/icons-vue";
import {queryAllApi as queryAllDeptApi} from '@/api/dept'

const jobs = ref([{name: '班主任', value: 1}, {name: '讲师', value: 2}, {name: '学工主管', value: 3}, {
  name: '教研主管',
  value: 4
}, {name: '咨询师', value: 5}, {name: '其他', value: 6}])
const genders = ref([{name: '男', value: 1}, {name: '女', value: 2}])
const deptList = ref([])

const searchEmp = ref({
  name: '',
  gender: '',
  date: [],
  begin: '',
  end: ''
})

const search = async () => {
  // 处理查询逻辑
  console.log('Search:', searchEmp.value)
  const result = await queryPageApi(
      searchEmp.value.name,
      searchEmp.value.gender,
      searchEmp.value.begin,
      searchEmp.value.end,
      currentPage.value,
      pageSize.value
  )
  if (result.code) {
    empList.value = result.data.rows
    total.value = result.data.total
  }
}

const clear = () => {
  // 清空查询条件
  searchEmp.value = {
    name: '',
    gender: '',
    date: [],
    begin: '',
    end: ''
  }
  search()
}

//侦听searchEmp中的date属性
watch(
    () => searchEmp.value.date,
    (newValue, oldValue) => {
      if (newValue.length == 2) {
        searchEmp.value.begin = newValue[0]
        searchEmp.value.end = newValue[1]
      } else {
        searchEmp.value.begin = ''
        searchEmp.value.end = ''
      }
    }
)

onMounted(async () => {
  search()
  const result = await queryAllDeptApi()
  if (result.code) {
    deptList.value = result.data
  }
  getToken()
})

//获取token
const getToken = () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if (loginUser && loginUser.token) {
    token.value = loginUser.token;
  }
}

// 示例数据
const empList = ref([
  {
    "id": 1,
    "username": "jinyong",
    "password": "123456",
    "name": "金庸",
    "gender": 1,
    "image": "https://web-framework.oss-cn-hangzhou.aliyuncs.com/2022-09-02-00-27-53B.jpg",
    "job": 2,
    "salary": 8000,
    "entryDate": "2015-01-01",
    "deptId": 2,
    "deptName": "教研部",
    "createTime": "2022-09-01T23:06:30",
    "updateTime": "2022-09-02T00:29:04"
  }
])

// 分页配置
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 分页处理
const handleSizeChange = (val) => {
  search()
}
const handleCurrentChange = (val) => {
  search()
}

//新增员工
const addEmp = () => {
  dialogVisible.value = true
  dialogTitle.value = '新增员工'
  //清空表单内容及校验提示信息
  employee.value = {
    username: '',
    name: '',
    gender: '',
    phone: '',
    job: '',
    salary: '',
    deptId: '',
    entryDate: '',
    image: '',
    exprList: []
  }
  if (employeeFormRef.value) {
    employeeFormRef.value.resetFields()
  }
}

//表单校验规则
// 验证规则
const rules = ref({
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 2, max: 20, message: '用户名长度应在2到20个字符之间', trigger: 'blur'}
  ],
  name: [
    {required: true, message: '请输入姓名', trigger: 'blur'},
    {min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur'}
  ],
  gender: [
    {required: true, message: '请选择性别', trigger: 'change'}
  ],
  phone: [
    {required: true, message: '请输入手机号', trigger: 'blur'},
    {pattern: /^1\d{10}$/g, message: '请输入有效的手机号', trigger: 'blur'}
  ]
})

//新增/修改表单
const employeeFormRef = ref(null)
const employee = ref({
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
})

// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('新增员工')

//文件上传
// 图片上传成功后触发
const handleAvatarSuccess = (response, uploadFile) => {
  employee.value.image = response.data
}
// 文件上传之前触发
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('图片大小不能超过10M')
    return false
  }
  return true
}

//工作经历
//动态添加工作经历
const addExprItem = () => {
  employee.value.exprList.push({exprDate: [], begin: '', end: '', company: '', job: ''})
}
//动态删除工作经历
const delExprItem = (index) => {
  employee.value.exprList.splice(index, 1)
}
//监听-employee员工对象中的工作经历数据
watch(() => employee.value.exprList, (newValue, oldValue) => {
  if (employee.value.exprList && employee.value.exprList.length > 0) {
    employee.value.exprList.forEach(expr => {
      expr.begin = expr.exprDate[0]
      expr.end = expr.exprDate[1]
    })
  }
}, {deep: true})
//保存员工信息
const save = async () => {
  employeeFormRef.value.validate(async valid => {
    if (valid) {
      let result
      if (employee.value.id) {
        result = await updateApi(employee.value)
      } else {
        result = await addApi(employee.value)
      }
      if (result.code) {
        ElMessage.success('新增员工成功')
        dialogVisible.value = false
        search()
      } else {
        ElMessage.error(result.msg)
      }
    }
  })
}
//编辑
const edit = async (id) => {
  const result = await queryInfoApi(id);
  if (result.code) {
    dialogVisible.value = true
    dialogTitle.value = '修改员工'
    employee.value = result.data
    //对工作经历进行处理
    let exprList = employee.value.exprList
    if (exprList && exprList.length > 0) {
      exprList.forEach((expr) => {
        expr.exprDate = [expr.begin, expr.end]
      })
    }
  }
}
//删除员工
const deleteById = (id) => {
  //弹出确认框
  ElMessageBox.confirm('此操作将永久删除该员工, 是否继续?', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    const result = await deleteApi(id)
    if (result.code) {
      ElMessage.success('删除成功')
      search()
    } else {
      ElMessage.error(result.msg)
    }
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}
//记录勾选的员工的id
const selectedIds = ref([])
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}
//批量删除
const deleteByIds = () => {
  ElMessageBox.confirm('此操作将永久删除选中的员工, 是否继续?', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    if (selectedIds.value && selectedIds.value.length > 0) {
      const result = await deleteApi(selectedIds.value)
      if (result.code) {
        ElMessage.success('删除成功')
        search()
      } else {
        ElMessage.error(result.msg)
      }
    } else {
      ElMessage.info('请选择要删除的员工')
    }
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}
</script>

<template>
  <h1>员工管理</h1>
  <div class="container">
    <el-form :inline="true" :model="searchEmp">
      <el-form-item label="姓名">
        <el-input v-model="searchEmp.name" placeholder="请输入员工姓名"/>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="searchEmp.gender" placeholder="请选择">
          <el-option label="男" value="1"/>
          <el-option label="女" value="2"/>
        </el-select>
      </el-form-item>
      <el-form-item label="入职日期">
        <el-date-picker
            v-model="searchEmp.date"
            type="daterange"
            range-separator="To"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button @click="clear">清空</el-button>
      </el-form-item>
    </el-form>

    <el-button type="primary" @click="addEmp"> + 新增员工</el-button>
    <el-button type="danger" @click="deleteByIds"> - 批量删除</el-button>
    <br>
    <!-- 表格 -->
    <el-table :data="empList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column prop="name" label="姓名" width="120" align="center"/>
      <el-table-column label="性别" width="120" align="center">
        <template #default="scope">
          {{ scope.row.gender == 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column label="头像" width="120" align="center">
        <template #default="scope">
          <img :src="scope.row.image" alt="Avatar" class="avatar">
        </template>
      </el-table-column>
      <el-table-column prop="deptName" label="部门名称" width="120" align="center"/>
      <el-table-column label="职位" width="120" align="center">
        <template #default="scope">
          <span v-if="scope.row.job == 1">班主任</span>
          <span v-else-if="scope.row.job == 2">讲师</span>
          <span v-else-if="scope.row.job == 3">学工主管</span>
          <span v-else-if="scope.row.job == 4">教研主管</span>
          <span v-else-if="scope.row.job == 5">咨询师</span>
          <span v-else>其他</span>
        </template>
      </el-table-column>
      <el-table-column prop="entryDate" label="入职日期" width="120" align="center"/>
      <el-table-column prop="updateTime" label="最后操作时间" width="180" align="center"/>
      <el-table-column label="操作" fixed="right" align="center">
        <template #default="scope">
          <el-button size="small" type="primary" @click="edit(scope.row.id)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <br>
    <!-- 分页 -->
    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 20, 30, 50, 75, 100]"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
    <!-- 新增/修改员工的对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle">
      <el-form ref="employeeFormRef" :model="employee" label-width="80px" :rules="rules">
        <!-- 基本信息 -->
        <!-- 第一行-->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="employee.username" placeholder="请输入员工用户名，2-20个字"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="employee.name" placeholder="请输入员工姓名，2-10个字"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行-->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="employee.gender" placeholder="请选择性别" style="width: 100%;">
                <el-option v-for="gender in genders" :key="gender.name" :label="gender.name"
                           :value="gender.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机" prop="phone">
              <el-input v-model="employee.phone" placeholder="请输入手机号码"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第三行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职位">
              <el-select v-model="employee.job" placeholder="请选择职位" style="width: 100%;">
                <el-option v-for="job in jobs" :key="job.name" :label="job.name" :value="job.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪资">
              <el-input v-model="employee.salary" placeholder="请输入员工薪资"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第四行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属部门">
              <el-select v-model="employee.deptId" placeholder="请选择部门" style="width: 100%;">
                <el-option v-for="dept in deptList" :key="dept.id" :label="dept.name" :value="dept.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期">
              <el-date-picker v-model="employee.entryDate" type="date" placeholder="选择日期" style="width: 100%;"
                              format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第五行 -->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="头像">
              <el-upload
                  class="avatar-uploader"
                  action="/api/upload"
                  :headers="{'token': token}"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload"
              >
                <img v-if="employee.image" :src="employee.image" class="avatar">
                <el-icon v-else class="avatar-uploader-icon">
                  <Plus></Plus>
                </el-icon>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 工作经历 -->
        <!-- 第六行 -->
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="工作经历">
              <el-button type="success" size="small" @click="addExprItem">+ 添加工作经历</el-button>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第七行 ...  工作经历 -->
        <el-row :gutter="3" v-for="(expr,index) in employee.exprList">
          <el-col :span="10">
            <el-form-item size="small" label="时间" label-width="80px">
              <el-date-picker type="daterange" v-model="expr.exprDate" range-separator="至" start-placeholder="开始日期"
                              end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small" label="公司" label-width="60px">
              <el-input placeholder="请输入公司名称" v-model="expr.company"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small" label="职位" label-width="60px">
              <el-input placeholder="请输入职位" v-model="expr.job"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="2">
            <el-form-item size="small" label-width="0px">
              <el-button type="danger" @click="delExprItem(index)">- 删除</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 底部按钮 -->
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.container {
  margin: 15px 0;
}

.avatar {
  height: 40px;
}

.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}
</style>
