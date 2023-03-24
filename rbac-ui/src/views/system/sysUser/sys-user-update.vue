<template>
<div>
  <el-form ref="form" :model="sysUser" :rules="rules" label-width="80px" size="small">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-form-item label="所在部门">
          <TreeSelect
            v-model="sysUser.deptId"
            :options="deptOptions"
            :normalizer="normalizer"
            :show-count="true"
            placeholder="选择所在部门"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="账号" prop="userName">
          <el-input v-model="sysUser.userName" placeholder="请输入账号" clearable />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="sysUser.nickName" placeholder="请输入昵称" clearable />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="sysUser.email" placeholder="请输入邮箱" clearable />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="sysUser.phone" placeholder="请输入手机号" clearable />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="sysUser.sex">
            <el-radio v-for="sex in sexOptions" :key="sex.dictValue" :label="sex.dictValue">{{ sex.dictLabel }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="sysUser.status">
            <el-radio v-for="status in statusOptions" :key="status.dictValue" :label="status.dictValue">{{ status.dictLabel }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>
    <el-form-item>
      <el-button type="primary" @click="doUpdate">修改</el-button>
      <el-button type="warning" @click="sysUser = {sex:0,status:0}">重置</el-button>
    </el-form-item>
  </el-form>

</div>
</template>

<script>
import sysDictDataApi from "@/api/sysDictData/sysDictData"
import sysDeptApi from "@/api/system/sysDept"
import TreeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {handleTree} from "@/utils/powernode";
import sysUserApi from "@/api/system/sysUser"
export default {
  name: "sys-user-update",
  components:{
    TreeSelect
  },
  props:{
    activeObj:{
      type:Object,
      default:undefined
    }
  },
  watch:{
    activeObj:{
      immediate:true,
      handler:function (newVal, oldVal) {
        this.sysUser = newVal
      }
    }
  },
  created() {
    this.loadDictDataByDictType()
    this.loadDictDataSexByDictType()
    this.loadAllDeptList()
  },
  data(){
    return{
      sysUser:[],
      rules:{
        userName: [{required: true, trigger: "blur", message: "不能为空"}],
        nickName: [{required: true, trigger: "blur", message: "不能为空"}],
        status: [{required: true, trigger: "blur", message: "不能为空"}],
        sex: [{required: true, trigger: "blur", message: "不能为空"}],
        phone: [{required: true, trigger: "blur", message: "不能为空"}],
        email: [{required: true, trigger: "blur", message: "不能为空"}],
      },
      statusOptions:[],
      sexOptions:[],
      deptOptions:[],
    }
  },
  methods:{
    doUpdate(){
      sysUserApi.update(this.sysUser).then(res=>{
        this.$message.success(res.msg)
        this.$emit("close")
        this.$emit("after")
      })
    },
    loadDictDataByDictType(){
      sysDictDataApi.getDictDataByDictType("sys_status_type").then(res=>{
        this.statusOptions = res.data
      })
    },
    loadDictDataSexByDictType(){
      sysDictDataApi.getDictDataByDictType("sys_user_sex").then(res=>{
        this.sexOptions = res.data
      })
    },
    loadAllDeptList(){
      sysDeptApi.listDept().then(res => {
        console.log("res",res)
        this.deptOptions = handleTree(res.data,"deptId")
      })
    },
    normalizer(node) {
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children,
      }
    },

  }


}
</script>

<style scoped>

</style>
