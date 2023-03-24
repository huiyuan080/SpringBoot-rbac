<template>
  <div>
    <el-form :rules="rules"  :model="form" ref="form"label-width="80px" size="small">
      <el-form-item label="角色名称" prop="roleName">
        <el-input placeholder="请输入字典名称" v-model="form.roleName"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="roleSort">
        <el-input-number v-model="form.roleSort" :min="1" :max="10" label="描述文字"></el-input-number>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" placeholder="请输入备注" v-model="form.remark"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio :label="0">正常</el-radio>
          <el-radio :label="1">停用</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <el-button size="mini" type="success" icon="el-icon-plus" style="margin-left: 15px" @click="doAdd">添 加
    </el-button>
    <el-button size="mini" type="info" icon="el-icon-refresh" style="margin-left: 15px" @click="doReset">重 置
    </el-button>

  </div>
</template>

<script>
import sysRoleApi from "@/api/system/sysRole"

export default {
  name: "sys-role-add",
  data() {
    return {
      form: {
        roleName: undefined,
        roleSort: undefined,
        remark: undefined,
        status: 0,
      },
      rules: {
        roleName: [{required: true, trigger: "blur", message: "不能为空"}],
        roleSort: [{required: true, trigger: "blur", message: "不能为空"}]
      }
    }
  },

  methods: {
    doAdd() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          sysRoleApi.save(this.form).then(res => {
            this.$message.success(res.msg)
            //子组件调用父组件的方法
            this.$emit("close")
            this.$emit("after")
            this.form = {
              status: 0
            }
            // console.log(res)
          })
        }
      })
    },
    doReset() {
      this.form = {
        dictName: undefined,
        dictType: undefined,
        remark: undefined,
        status: 1
      }
    }
  }

}
</script>

<style scoped>

</style>
