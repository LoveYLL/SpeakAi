<template>
  <view class="pagination-container" v-if="total > 0">
    <view class="pagination-content">
      <view class="pagination-info">
        {{ current }}/{{ totalPages }}页
      </view>
      
      <view class="pagination-controls">
        <button 
          class="page-btn prev-btn"
          :disabled="current <= 1"
          @tap="$emit('prev')"
        >
          <svg class="btn-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <path d="M15 18l-6-6 6-6" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </button>
        
        <view class="page-numbers">
          <button
            v-for="page in visiblePages"
            :key="page"
            :class="['page-number', { active: page === current }]"
            @tap="$emit('change', page)"
          >
            {{ page }}
          </button>
          <text v-if="showEllipsis" class="ellipsis">...</text>
        </view>
        
        <button 
          class="page-btn next-btn"
          :disabled="current >= totalPages"
          @tap="$emit('next')"
        >
          <svg class="btn-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <path d="M9 18l6-6-6-6" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </button>
      </view>
      
      <select v-model="internalPageSize" @change="$emit('size-change', internalPageSize)" class="size-select">
        <option v-for="size in pageSizeOptions" :key="size" :value="size">{{ size }}</option>
      </select>
    </view>
  </view>
</template>
<style scoped>
.pagination-container {
  display: flex;
  justify-content: center;
  margin: 20px 0;
  font-family: "Plus Jakarta Sans", sans-serif;
}

.pagination-content {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 12px;
  background: white;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.pagination-info {
  font-size: 13px;
  color: #6b7280;
  font-weight: 500;
  white-space: nowrap;
}

.pagination-controls {
  display: flex;
  align-items: center;
  gap: 4px;
}

.page-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 6px;
  border: 1px solid #d1d5db;
  background: white;
  color: #374151;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s ease;
  width: 28px;
  height: 28px;
}

.page-btn:hover:not(:disabled) {
  border-color: #3f8cbf;
  background: #f0f9ff;
}

.page-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  background: #f9fafb;
}

.btn-icon {
  width: 14px;
  height: 14px;
}

.page-numbers {
  display: flex;
  align-items: center;
  gap: 2px;
}

.page-number {
  padding: 4px 8px;
  border: 1px solid #d1d5db;
  background: white;
  color: #374151;
  border-radius: 4px;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.2s ease;
  min-width: 24px;
  height: 24px;
  text-align: center;
  line-height: 1;
}

.page-number:hover {
  border-color: #3f8cbf;
  background: #f0f9ff;
}

.page-number.active {
  background: #3f8cbf;
  color: white;
  border-color: #3f8cbf;
  font-weight: 600;
}

.ellipsis {
  padding: 4px 2px;
  color: #9ca3af;
  font-size: 12px;
}

.size-select {
  padding: 4px 8px;
  border: 1px solid #d1d5db;
  border-radius: 4px;
  background: white;
  color: #374151;
  font-size: 12px;
  cursor: pointer;
  height: 28px;
}

.size-select:hover {
  border-color: #3f8cbf;
}

/* 响应式微调 */
@media (max-width: 480px) {
  .pagination-content {
    gap: 8px;
    padding: 6px 8px;
  }
  
  .pagination-info {
    font-size: 12px;
  }
  
  .page-number {
    padding: 3px 6px;
    font-size: 11px;
    min-width: 22px;
    height: 22px;
  }
}
</style>

<script setup>
import { computed, ref, watch } from 'vue'

const props = defineProps({
  current: {
    type: Number,
    default: 1
  },
  total: {
    type: Number,
    default: 0
  },
  pageSize: {
    type: Number,
    default: 10
  },
  pageSizeOptions: {
    type: Array,
    default: () => [5, 10, 20, 50]
  },
  maxVisiblePages: {
    type: Number,
    default: 5
  }
})

const emit = defineEmits(['change', 'prev', 'next', 'size-change'])

const internalPageSize = ref(props.pageSize)

watch(() => props.pageSize, (newVal) => {
  internalPageSize.value = newVal
})

const totalPages = computed(() => Math.ceil(props.total / props.pageSize))

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, props.current - Math.floor(props.maxVisiblePages / 2))
  const end = Math.min(totalPages.value, start + props.maxVisiblePages - 1)
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  return pages
})

const showEllipsis = computed(() => {
  return totalPages.value > props.maxVisiblePages
})
</script>