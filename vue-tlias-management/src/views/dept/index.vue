<script setup>
import {ref, onMounted} from 'vue';
import axios from "axios";

// 示例数据
// const deptList = ref([
//   {id: 1, name: '学工部', createTime: '2024-09-01T23:06:29', updateTime: '2024-09-01T23:06:29'},
//   {id: 2, name: '教研部', createTime: '2024-09-01T23:06:29', updateTime: '2024-09-01T23:06:29'}
// ]);

let deptList = ref([]);

const queryAll = async () => {
  const result = await axios.get('https://localhost:8080/depts');
  deptList.value = result.data.data;
}

onMounted(() => {
  queryAll();
})

// 编辑部门 - 根据ID查询回显数据
const handleEdit = (id) => {
  console.log(`Edit item with ID ${id}`);
}

// 删除部门 - 根据ID删除部门
const handleDelete = (id) => {
  console.log(`Delete item with ID ${id}`);
  // 在这里实现删除功能
};
</script>

<template>
  <h1>部门管理</h1>

  <!-- 按钮靠页面右侧显示 -->
  <el-button type="primary" @click="handleEdit" style="float: right;">+新增部门</el-button>
  <br>

  <el-table :data="deptList" border style="width: 100%;">
    <el-table-column type="index" label="序号" width="100" align="center"></el-table-column>
    <el-table-column prop="name" label="部门名称" width="300" align="center"></el-table-column>
    <el-table-column prop="updateTime" label="最后修改时间" width="300" align="center"></el-table-column>
    <el-table-column fixed="right" label="操作" align="center">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.row.id)">修改</el-button>
        <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>
