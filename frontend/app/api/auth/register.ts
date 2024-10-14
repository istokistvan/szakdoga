"use server"

export async function handleRegister(formData: FormData) {
    await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        body: formData,
    })
        .catch(err => console.error(err))
}