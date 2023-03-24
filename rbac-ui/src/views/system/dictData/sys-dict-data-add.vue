<template>
  <div>
    <el-form :model="form" ref="form" :rules="rules" label-width="80px" size="small">
      <el-form-item label="字典类型" prop="dictType" >
        <el-input v-model="form.dictType" disabled></el-input>
      </el-form-item>
      <el-form-item label="数据标签" prop="dictLabel">
        <el-input v-model="form.dictLabel"></el-input>
      </el-form-item>
      <el-form-item label="数据键值" prop="dictValue">
        <el-input v-model="form.dictValue"></el-input>
      </el-form-item>
      <el-form-item label="显示排序" prop="dictSort">
        <el-input v-model="form.dictSort"></el-input>
      </el-form-item>
      <el-form-item label="皮肤属性" prop="dictSkin">
        <el-select value="" v-model="form.dictSkin">
          <el-option value="success" label="成功"></el-option>
          <el-option value="info" label="信息"></el-option>
          <el-option value="error" label="错误"></el-option>
          <el-option value="danger" label="危险"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" v-model="form.remark"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status ">
        <el-radio-group v-model="form.status">
          <el-radio :label="0">正常</el-radio>
          <el-radio :label="1">停用</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <el-button size="small" type="success" @click="doAdd">添加</el-button>
    <el-button size="small" type="info" @click="doReset">重置</el-button>
  </div>
</template>

<script>
import sysDictDataApi from "@/api/sysDictData/sysDictData"

export default {
  name: "sys-dict-data-add",
  //父子传参
  props: {
    dictTypeSon: {
      default: undefined,
      type: String
    }
  },
  //监听器
  watch:{
    dictTypeSon:{
      immediate:true,
      handler:function (newVal,oldVal) {
        this.form.dictType= this.dictTypeSon
      }
    }
  },

  data() {
    return {
      form: {
        dictLabel: undefined,
        dictType: undefined,
        dictValue: undefined,
        dictSort: undefined,
        dictSkin: undefined,
        status: undefined,
        remark: undefined,
      },
      rules: {
        dictLabel: [{required: true, trigger: "blur", message: "不能为空"}],
        dictValue: [{required: true, trigger: "blur", message: "不能为空"}],
        dictSort: [{required: true, trigger: "blur", message: "不能为空"}],
      },
    }
  },
  methods: {

    doAdd() {
      this.$refs['form'].validate(valid=>{
        if (valid){
          sysDictDataApi.save(this.form).then(res => {
            console.log(res)
            this.$emit("after")
            this.$emit("showdown")

          })
        }
      })

    },
    doReset() {
      this.form = {
        dictType: this.dictTypeSon,
        status: 0
      }
    }


  }

}
</script>

<style scoped>

</style>
