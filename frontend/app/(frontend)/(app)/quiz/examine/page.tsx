"use client"

import {useCallback, useEffect, useState} from "react";
import {getAllQuiz} from "@/app/api/quiz/examine/examine";
import type {Examine} from "@/app/lib/definitions";
import {Button, Card, CardBody, CardFooter, CardHeader, Divider, Link} from "@nextui-org/react";

export default function Examine() {

    const [state, setState] = useState<Examine[]>([])

    useEffect(() => {
        getAllQuiz().then(res => setState(res))
    }, []);

    const renderQuizzes = useCallback(() => {
        return state.map((quiz, index) => (
            <Card key={index} className="w-fit min-w-[400px] h-fit">
                <CardHeader
                    className="flex justify-center text-xl font-bold"
                >
                    {quiz.name}
                </CardHeader>

                <Divider/>

                {quiz.description &&
                    <CardBody>
                        <p>{quiz.description}</p>
                    </CardBody>}

                <Divider/>

                <CardFooter className="flex justify-around">
                    <Link
                        href={`/quiz/examine/${quiz.id}`}
                    >
                        <Button
                            color="danger"
                        >
                            Take Exam
                        </Button>
                    </Link>
                </CardFooter>
            </Card>
        ))
    }, [state])

    return (
        <div className="w-full h-fit flex flex-wrap gap-5 justify-between">
            {renderQuizzes()}
        </div>
    )
}