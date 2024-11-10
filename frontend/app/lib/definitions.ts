export const AUTHORIZATION = 'Authorization'

export const questionTypes = [
    {
        value: "MULTIPLE_CHOICE",
        label: "Multiple choice"
    },
    {
        value: "ONE_CHOICE",
        label: "One choice"
    },
    {
        value: "TRUE_FALSE",
        label: "True or false"
    },
    {
        value: "NUMBER",
        label: "Number"
    }
]

export type Answer = {
    answer: string;
    isCorrect: boolean;
    errorTolerance: number;
};

export type Question = {
    question: string;
    questionType: string;
    points: number;
    isNegated: boolean;
    answers: Answer[];
};

export type Quiz = {
    name: string,
    isVisible: boolean,
    password: string,
    description: string,
    availableFrom: string,
    availableTo: string,
    questions: Question[]
}

export type Study = {
    id: string,
    name: string,
    description: string,
    questions: Question[]
}