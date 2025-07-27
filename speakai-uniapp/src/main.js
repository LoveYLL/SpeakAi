import { createSSRApp } from "vue";
import App from "./App.vue";

uni.$on('beforeRouteEnter',(to,from,next) => {
	console.log('要去：'+to.path);
	next()
})
export function createApp() {
	const app = createSSRApp(App);
	return {
		app,
	};
}
