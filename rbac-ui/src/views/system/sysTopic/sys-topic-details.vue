<template>
  <div>

    <el-descriptions class="margin-top" :column="2" :model="topicList">
      <el-descriptions-item label="主题">{{ topicList.title }}</el-descriptions-item>
      <el-descriptions-item label="作者">{{ topicList.author }}</el-descriptions-item>
    </el-descriptions>
    <el-divider/>
    <el-descriptions class="margin-top" :column="2" :model="topicList" >
      <el-descriptions-item label="内容">{{ topicList.content }}</el-descriptions-item>
      <el-descriptions-item label="发布时间">{{ topicList.createDate }}</el-descriptions-item>
    </el-descriptions>
    <el-divider/>

    <template v-for="re in replyList" >

      <el-descriptions class="margin-top" :column="2">
        <el-descriptions-item label="回复人">{{ re.author }}</el-descriptions-item>
        <el-descriptions-item label="回复时间">{{ re.createDate }}</el-descriptions-item>
        <el-descriptions-item label="回复内容">{{ re.content }}</el-descriptions-item>
      </el-descriptions>
      <hr/>
    </template>

    <el-descriptions class="margin-top" :column="2">
      <el-descriptions-item label="回复人">{{ this.countNum }}人</el-descriptions-item>
    </el-descriptions>
    <el-form size="mini" label-width="80px" :model="form" ref="form" :rules="rules">
      <el-form-item label="回复人" prop="author">
        <el-input v-model="form.author"></el-input>
      </el-form-item>
      <el-form-item label="回复内容" prop="content">
        <el-input v-model="form.content"></el-input>
      </el-form-item>
      <div align="center">
        <el-button size="small" type="success" icon="el-icon-check" @click="doReply">回复</el-button>
        <el-button size="small" type="info" icon="el-icon-refresh" @click="doReset">重置</el-button>
      </div>
    </el-form>

  </div>
</template>

<script>
import sysReplyApi from "@/api/system/sysReply"
import sysTopicApi from "@/api/system/sysTopic"

export default {
  name: "sys-topic-details",
  created() {
    this.doSearch()
  },
  props: {
    activeObj: {
      type: Object,
      default: undefined
    }
  },
  watch: {
    activeObj: {
      immediate: true,
      handler: function (newVal, oldVal) {
        this.topicList = this.activeObj
        this.form.topicId = this.activeObj.topicId
        sysTopicApi.add(this.activeObj.topicId).then(res => {
          this.$emit("after")
        })
      }
    }
  },
  data() {
    return {
      topicList: [],
      replyList: [],
      countNum: undefined,
      form: {
        topicId: undefined,
        author: undefined,
        content: undefined
      },
      rules: {
        author: [{required: true, trigger: "blur", message: "不能为空"}],
        content: [{required: true, trigger: "blur", message: "不能为空"}],
      }
    }
  },
  methods: {
    doReply() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          sysReplyApi.save(this.form).then(res => {
            this.doReset()
            this.doSearch()
          })
        }
      })
    },

    doReset() {
      this.form = {}
    },
    doSearch() {
      sysReplyApi.get(this.activeObj.topicId).then(res => {
        this.replyList = res.data
      })
      sysReplyApi.countNum(this.activeObj.topicId).then(res => {
        this.countNum = res.data
      })

    }
  }


}
</script>

<style scoped>

</style>
