"use client"

import {Button} from "@nextui-org/react";

export default function QuizCreateButton() {

    return (
        <Button
            isIconOnly
            className="rounded-full p-8 font-bold text-2xl shadow-lg bg-gradient-to-t from-yellow-400 to-orange-600 text-white"
        >
            +
        </Button>
    )
}