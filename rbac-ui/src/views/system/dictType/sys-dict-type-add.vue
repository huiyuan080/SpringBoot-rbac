<template>

  <div>
    <el-form :rules="rules" label-width="80px" size="small" :model="form" ref="form">
      <el-form-item label="字典名称" prop="dictName">
        <el-input placeholder="请输入字典名称" v-model="form.dictName"></el-input>
      </el-form-item>
      <el-form-item label="字典类型" prop="dictType">
        <el-input placeholder="请输入字典类型" v-model="form.dictType"></el-input>
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
import sysDictTypeApi from '@/api/sysDictType/sysDictType'

export default {
  name: "sys-dict-type-add",
  data() {
    return {
      form: {
        dictName: undefined,
        dictType: undefined,
        remark: undefined,
        status: 0,
      },
      rules: {
        dictName: [{required: true, trigger: "blur", message: "不能为空"}],
        dictType: [{required: true, trigger: "blur", message: "不能为空"}]
      }
    }
  },

  methods: {
    doAdd() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          sysDictTypeApi.save(this.form).then(res => {
            this.$message.success(res.msg)
            //子组件调用父组件的方法
            this.$emit("after")
            this.$emit("showdown")
            this.form={
              status: 0
            }
            // console.log(res)
          }).catch(() => {
            this.$message.error("添加失败")
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
