import { createRouter, createWebHistory, type RouteRecordRaw, type RouteLocationNormalized } from 'vue-router'

const routes: RouteRecordRaw[] = [
    {
        path: '/',
        name: 'Home',
        component: () => import('../views/Home.vue'),
        props: (route: RouteLocationNormalized) => ({
            user: route.query.user as string || 'default',
            theme: route.query.theme as string || 'light'
        })
    },
    {
        path: '/chat',
        name: 'Chat',
        component: () => import('../views/Chat.vue'),
        props: (route: RouteLocationNormalized) => ({
            userId: route.query.userId as string | null
        })
    }
]

// 3. 创建路由器实例
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router