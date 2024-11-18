"use client"

import {useCallback, useEffect, useState} from "react";
import {getCreatedQuizzes, getFilledQuizNumber} from "@/app/api/dashboard/dashboard";
import {Quiz} from "@/app/lib/definitions";
import {Card, CardBody, CardHeader, Divider, Snippet} from "@nextui-org/react";

export default function DashBoard() {

    const [quizNumber, setQuizNumber] = useState<number>()
    const [createdQuizzes, setCreatedQuizzes] = useState<Quiz[]>()

    useEffect(() => {
        getFilledQuizNumber().then(res => res && setQuizNumber(parseInt(res)))
        getCreatedQuizzes().then(res => setCreatedQuizzes(res))
    }, []);

    const renderQuizzes = useCallback(() => {
        return createdQuizzes?.map((quiz, index) => (
            <Card key={index} className="w-fit min-w-[400px] h-fit">
                <CardHeader
                    className="flex justify-center text-xl font-bold"
                >
                    {quiz.name}
                </CardHeader>

                <Divider/>

                <CardBody>
                    {quiz.description &&
                        <p>{quiz.description}</p>
                    }
                    <Snippet symbol="Share Id:" variant="bordered">
                        {quiz.id}
                    </Snippet>
                </CardBody>
            </Card>
        ))
    }, [createdQuizzes])

    return (
        <div
            className="grid grid-cols-2"
        >
            <div
                className="w-full h-full flex flex-col gap-5 justify-start items-center"
            >
                <h1
                    className="text-2xl font-bold"
                >
                    Statistics
                </h1>
                <p>Filled quizzes: {quizNumber}</p>

            </div>

            <div
                className="w-full h-full flex flex-col gap-5 justify-center items-center"
            >
                <h1
                    className="text-2xl font-bold"
                >
                    My Quizzes
                </h1>
                {renderQuizzes()}
            </div>
        </div>
    )
}