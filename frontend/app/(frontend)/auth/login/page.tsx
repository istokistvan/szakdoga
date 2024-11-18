"use client"

import {Button, Input, Link} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import {handleLogin} from "@/app/api/auth/login";

export default function LoginPage() {

    const router = useRouter()

    const handleSubmit = async (formData: FormData) => {
        handleLogin(formData).then((res) => {
            if (res) {
                router.replace("/dashboard")
            }
        })
    }

    return (
        <div
            className="w-full h-full p-5"
        >
            <h1
                className="text-3xl font-bold text-center"
            >
                Login
            </h1>

            <form
                action={handleSubmit}
                className="flex flex-col w-full h-full justify-evenly items-center"
            >
                <Input
                    type="text"
                    name="userName"
                    label="Username"
                    isRequired
                    variant="underlined"
                />

                <Input
                    type="password"
                    name="password"
                    label="Password"
                    isRequired
                    variant="underlined"
                />

                <Link href="/auth/register">Don&apos;t have an account? Register</Link>
                <Button type="submit" color="success">Login</Button>
            </form>
        </div>
    );
}