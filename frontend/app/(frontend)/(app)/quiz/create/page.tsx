"use client"

import {Button, DatePicker, Input, Switch, Textarea} from "@nextui-org/react";
import {useState} from "react";
import {RiEyeCloseLine, RiEyeLine} from "react-icons/ri"
import {getLocalTimeZone, now} from "@internationalized/date";
import QuestionForm from "@/app/ui/QuizCreation/QuestionForm";
import {Question, Quiz} from "@/app/lib/definitions";
import {DateTime} from "luxon";
import {useRouter} from "next/navigation";
import createQuiz from "@/app/api/quiz/create/create";

export default function QuizCreation() {

    const router = useRouter()

    const [isSelected, setIsSelected] = useState(true)
    const [state, setState] = useState<Question[]>([])

    const handleSubmit = (formData: FormData) => {
        const quizData: Quiz = {
            name: formData.get('name') as string,
            isVisible: formData.get('isVisible') === '',
            password: formData.get('password') as string,
            description: formData.get('description') as string,
            availableFrom: DateTime.fromISO(formData.get('availableFrom') as string).toFormat('yyyy.MM.dd HH:mm'),
            availableTo: DateTime.fromISO(formData.get('availableTo') as string).toFormat('yyyy.MM.dd HH:mm'),
            questions: state
        }

        createQuiz(quizData)
            .then(() => router.replace('/dashboard'))
    }

    return (
        <div>
            <h1 className="text-center text-2xl font-bold mb-5">Quiz creation</h1>

            <form
                action={handleSubmit}
                className="grid grid-cols-2"
            >
                <div
                    className="w-full h-full flex flex-col gap-5 px-3"
                >
                    <h2
                        className="text-xl font-bold text-center"
                    >
                        Quiz details
                    </h2>

                    <Input
                        name="name"
                        label="Name"
                        isRequired

                        variant="underlined"
                    />

                    <div>
                        <Switch
                            isSelected={isSelected}
                            onValueChange={setIsSelected}

                            name="isVisible"

                            color="success"
                            startContent={<RiEyeLine/>}
                            endContent={<RiEyeCloseLine/>}
                        >
                            {isSelected ? "Public" : "Private"} quiz
                        </Switch>

                        <Input
                            name="password"
                            label="Password (optional)"
                            type="password"

                            variant="underlined"
                        />
                    </div>

                    <Textarea
                        name="description"
                        label="Description (optional)"
                    />

                    <div className="flex gap-5">
                        <DatePicker
                            name="availableFrom"
                            label="Available from"
                            granularity="minute"
                            defaultValue={now(getLocalTimeZone())}
                            isRequired
                        />

                        <DatePicker
                            name="availableTo"
                            label="Available to"
                            granularity="minute"
                            defaultValue={now(getLocalTimeZone()).add({days: 7})}
                            isRequired
                        />
                    </div>
                    <Button type="submit" color="success">Create quiz</Button>
                </div>

                <div className="w-full h-full flex flex-col gap-5 px-3 mb-6">
                    <h2
                        className="text-xl font-bold text-center"
                    >
                        Questions
                    </h2>

                    <QuestionForm setQuestions={setState}/>
                </div>

            </form>
        </div>
    )
}