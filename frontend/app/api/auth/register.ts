"use server"

export async function handleRegister(formData: FormData) {
    return fetch("http://backend:8080/api/auth/register", {
        method: "POST",
        body: formData,
    })
        .then(res => res.status < 400)
        .catch(err => console.error(err))
}