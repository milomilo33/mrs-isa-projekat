export async function getHelloWorld() {
    const response = await fetch('/api/helloworld');
    return await response.json();
}