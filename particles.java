const controls = new OrbitControls(camera, renderer.domElement);
// Ejemplo de cámara para el cubo: probar valores para mejor vista
camera.position.set(0, 5, 10);
controls.update();

//cuborubik
const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, window.innerWidth/window.innerHeight, 0.1, 1000);
const renderer = new THREE.WebGLRenderer({ alpha: true });
renderer.setSize(window.innerWidth, window.innerHeight);
document.getElementById('rubik-bg').appendChild(renderer.domElement);

const rubikGroup = new THREE.Group();
const cubeSize = 1, gap = 0.07;
const colors = [0xffffff, 0xff0000, 0x0000ff, 0x00ff00, 0xffff00, 0xff8000];

function makeStickeredCube(x, y, z) {
    const materials = [
        new THREE.MeshBasicMaterial({color: colors[0]}),
        new THREE.MeshBasicMaterial({color: colors[1]}),
        new THREE.MeshBasicMaterial({color: colors[2]}),
        new THREE.MeshBasicMaterial({color: colors[3]}),
        new THREE.MeshBasicMaterial({color: colors[4]}),
        new THREE.MeshBasicMaterial({color: colors[5]})
    ];
    const geometry = new THREE.BoxGeometry(cubeSize, cubeSize, cubeSize);
    const cube = new THREE.Mesh(geometry, materials);
    cube.position.set(x, y, z);
    return cube;
}
for (let x = -1; x <= 1; x++) {
    for (let y = -1; y <= 1; y++) {
        for (let z = -1; z <= 1; z++) {
            rubikGroup.add(makeStickeredCube(
                x * (cubeSize + gap),
                y * (cubeSize + gap),
                z * (cubeSize + gap)
            ));
        }
    }
}

scene.add(rubikGroup);

// Ahora la cámara y controles interactivos:
camera.position.set(0, 5, 10); // apúntalo bien al centro
const controls = new OrbitControls(camera, renderer.domElement);
controls.enableDamping = true;
controls.dampingFactor = 0.09;
controls.enableZoom = false;
controls.enablePan = false;
controls.update();

function animate() {
    requestAnimationFrame(animate);
    controls.update();
    renderer.render(scene, camera);
}
animate();
window.addEventListener('resize', () => {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
});

