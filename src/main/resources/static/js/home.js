// // 1. 필요한 Three.js 라이브러리를 포함합니다.
// //import * as THREE from 'three';
//
// // 2. 기본 설정: 렌더러, 카메라, 씬을 생성합니다.
// const scene = new THREE.Scene();
// const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
// const renderer = new THREE.WebGLRenderer();
// renderer.setSize(window.innerWidth, window.innerHeight);
// document.body.appendChild(renderer.domElement);
//
// // 3. 컴퓨터 모니터 모양을 만듭니다: 박스 지오메트리를 사용합니다.
// const geometry = new THREE.BoxGeometry(3, 2, 0.1);
// const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
// const monitor = new THREE.Mesh(geometry, material);
// scene.add(monitor);
//
// // 4. 애니메이션 기능을 추가합니다.
// function animate() {
//     requestAnimationFrame(animate);
//
//     // 모니터 애니메이션: 여기에서는 간단하게 회전만 적용합니다.
//     monitor.rotation.x += 0.01;
//     monitor.rotation.y += 0.01;
//
//     renderer.render(scene, camera);
// }
//
// // 5. 카메라 위치를 조정하고 애니메이션을 시작합니다.
// camera.position.z = 5;
// animate();