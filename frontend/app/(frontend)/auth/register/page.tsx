"use client"

import {useRouter} from "next/navigation";
import {Button, Input, Link} from "@nextui-org/react";
import {handleRegister} from "@/app/api/auth/register";

export default function RegisterPage() {

    const router = useRouter()

    const handleSubmit = async (formData: FormData) => {
        handleRegister(formData).then(() => {
            router.replace("/auth/login")
        })
    }

    return (
        <div
            className="w-full h-screen lg:col-start-2"
        >
            <h1
                className="text-3xl font-bold text-center"
            >
                Register
            </h1>
            <form
                action={handleSubmit}
                className="flex flex-col w-full h-full justify-evenly items-center"
            >
                <Input
                    type="text"
                    label="Full name"
                    name="fullName"
                    isRequired
                    variant="underlined"
                />


                <Input
                    type="text"
                    label="Username"
                    name="userName"
                    isRequired
                    variant="underlined"/>

                <Input
                    type="email"
                    label="Email"
                    name="email"
                    isRequired
                    variant="underlined"
                />
                <Input
                    type="password"
                    label="Password"
                    name="password"
                    isRequired
                    variant="underlined"
                />

                <Link href={"/auth/login"}>Already have an account? Login</Link>
                <Button
                    type="submit"
                    color="success"
                    variant="shadow"
                >
                    Register
                </Button>
            </form>
        </div>
    )

}