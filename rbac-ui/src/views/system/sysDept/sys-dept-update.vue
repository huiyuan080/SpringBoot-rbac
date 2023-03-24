<template>
  <div>
    <el-form label-width="80px" :model="form" ref="form" :rules="rules">
      <el-col :span="24">
        <el-form-item label="上级部门" prop="parentId">
          <!--          <el-input v-model="form.parentId" placeholder="请选择部门"></el-input>-->
          <TreeSelect v-model="form.parentId" showCount :normalizer="normalizer" :options="deptList"
                      placeholder="请选择部门"></TreeSelect>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="部门名称" prop="deptName">
          <el-input v-model="form.deptName" placeholder="请输入部门名称"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="显示排序" prop="orderNum">
          <!--          <el-input v-model="form.orderNum" placeholder="请选择排序"></el-input>-->
          <el-input-number v-model="form.orderNum" controls-position="right"
                           :min="1" :max="10"></el-input-number>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="负责人" prop="leader">
          <el-input v-model="form.leader" placeholder="请输入负责人"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="部门状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="0">正常</el-radio>
            <el-radio :label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
      <el-button type="primary" align="center" @click="doUpdate">修改</el-button>

    </el-form>


  </div>
</template>

<script>
import TreeSelect from "@riophae/vue-treeselect";
import sysDeptApi from "@/api/system/sysDept"
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import sysDictData from "@/api/sysDictData/sysDictData";
import {handleTree} from "@/utils/powernode";

export default {
  props: {
    activeId: {
      default: undefined,
      type: Number
    }
  },
  watch: {
    activeId: {
      immediate: true,
      handler: function (newVal, oldVal) {
        sysDeptApi.get(this.activeId).then(res => {
          this.form = res.data
          if (res.data.parentId == 0) {
            this.form.parentId = res.data.deptName
          }
        })
      }
    }
  },

  created() {
    this.loadDictDataByDictType()
    this.loadDeptList()
  },
  components: {
    TreeSelect
  },
  name: "sys-dept-update",
  data() {
    return {
      deptList: [],
      form: {},
      rules: {
        parentId: [{required: true, trigger: "blur", message: "不能为空"}],
        deptName: [{required: true, trigger: "blur", message: "不能为空"}],
        orderNum: [{required: true, trigger: "blur", message: "不能为空"}],
        leader: [{required: true, trigger: "blur", message: "不能为空"}],
        phone: [{required: true, trigger: "blur", message: "不能为空"}],
        email: [{required: true, trigger: "blur", message: "不能为空"}],
      }
    }
  },
  methods: {
    doUpdate() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          sysDeptApi.update(this.form).then(res => {
            this.$message.success("修改成功")
            this.$emit("after")
            this.$emit("showdown")
          })
        }
      })
    },

    loadDictDataByDictType() {
      sysDictData.getDictDataByDictType("sys_user_sex").then(res => {
        this.statusOptions = res.data
      })
    },
    loadDeptList() {
      sysDeptApi.listDept().then(res => {
        this.deptList = handleTree(res.data, "deptId")
        // console.log(res.data)
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
