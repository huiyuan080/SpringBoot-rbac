<template>
  <div>
    <el-form :model="form" ref="form" :rules="rules" label-width="80px">
      <el-form-item label="主题" prop="title">
        <el-input placeholder="请输入主题" v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input placeholder="请输入作者" v-model="form.author"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input placeholder="xxxxxx@qq.com" v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input type="textarea" placeholder="请输入帖子内容" v-model="form.content"></el-input>
      </el-form-item>
    </el-form>
    <div align="center">
      <el-button size="small" type="success" icon="el-icon-check" @click="doAdd">发布</el-button>
      <el-button size="small" type="info" icon="el-icon-refresh" @click="doReset">重置</el-button>
    </div>

  </div>
</template>

<script>
import sysTopicApi from "@/api/system/sysTopic"

export default {
  name: "sys-topic-add",
  data() {
    return {
      form: {
        title: undefined,
        author: undefined,
        email: undefined,
        content: undefined,
      },
      rules: {
        title: [{required: true, trigger: "blur", message: "不能为空"}],
        author: [{required: true, trigger: "blur", message: "不能为空"}],
        email: [{required: true, trigger: "blur", message: "不能为空"}],
        content: [{required: true, trigger: "blur", message: "不能为空"}],
      }

    }
  },
  methods: {
    doAdd() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          sysTopicApi.save(this.form).then(res => {
            this.$message.success("添加成功")
            this.$emit("close")
            this.$emit("after")
          })
        }
      })
    },

    doReset() {
      this.form = {}
    }


  }
}
</script>

<style scoped>

</style>
