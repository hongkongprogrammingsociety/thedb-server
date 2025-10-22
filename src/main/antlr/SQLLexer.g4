lexer grammar SQLLexer;

// Whitespace
WS : [ \t\r\n]+ -> skip;

// Comments
SINGLE_LINE_COMMENT : '--' ~[\r\n]* -> skip;
MULTI_LINE_COMMENT : '/*' .*? '*/' -> skip;
HASH_COMMENT : '#' ~[\r\n]* -> skip;

// Keywords - DDL
CREATE : C R E A T E;
DROP : D R O P;
ALTER : A L T E R;
TRUNCATE : T R U N C A T E;
RENAME : R E N A M E;

// Keywords - DML
SELECT : S E L E C T;
INSERT : I N S E R T;
UPDATE : U P D A T E;
DELETE : D E L E T E;
REPLACE : R E P L A C E;

// Keywords - Table/Database
TABLE : T A B L E;
DATABASE : D A T A B A S E;
SCHEMA : S C H E M A;
INDEX : I N D E X;
VIEW : V I E W;
TRIGGER : T R I G G E R;
PROCEDURE : P R O C E D U R E;
FUNCTION : F U N C T I O N;

// Keywords - Clauses
FROM : F R O M;
WHERE : W H E R E;
GROUP : G R O U P;
BY : B Y;
HAVING : H A V I N G;
ORDER : O R D E R;
LIMIT : L I M I T;
OFFSET : O F F S E T;
INTO : I N T O;
VALUES : V A L U E S;
SET : S E T;

// Keywords - Join types
JOIN : J O I N;
INNER : I N N E R;
LEFT : L E F T;
RIGHT : R I G H T;
FULL : F U L L;
OUTER : O U T E R;
CROSS : C R O S S;
NATURAL : N A T U R A L;
ON : O N;
USING : U S I N G;

// Keywords - Logical operators
AND : A N D;
OR : O R;
NOT : N O T;
IN : I N;
BETWEEN : B E T W E E N;
LIKE : L I K E;
IS : I S;
EXISTS : E X I S T S;
ALL : A L L;
ANY : A N Y;
SOME : S O M E;

// Keywords - Data types
INT : I N T;
INTEGER : I N T E G E R;
BIGINT : B I G I N T;
SMALLINT : S M A L L I N T;
TINYINT : T I N Y I N T;
DECIMAL : D E C I M A L;
NUMERIC : N U M E R I C;
FLOAT : F L O A T;
DOUBLE : D O U B L E;
REAL : R E A L;
VARCHAR : V A R C H A R;
CHAR : C H A R;
TEXT : T E X T;
BLOB : B L O B;
DATE : D A T E;
TIME : T I M E;
DATETIME : D A T E T I M E;
TIMESTAMP : T I M E S T A M P;
BOOLEAN : B O O L E A N;
BOOL : B O O L;

// Keywords - Constraints
PRIMARY : P R I M A R Y;
KEY : K E Y;
FOREIGN : F O R E I G N;
REFERENCES : R E F E R E N C E S;
UNIQUE : U N I Q U E;
NULL : N U L L;
DEFAULT : D E F A U L T;
AUTO_INCREMENT : A U T O '_' I N C R E M E N T;
CHECK : C H E C K;
CONSTRAINT : C O N S T R A I N T;

// Keywords - Modifiers
ADD : A D D;
MODIFY : M O D I F Y;
CHANGE : C H A N G E;
COLUMN : C O L U M N;
AFTER : A F T E R;
FIRST : F I R S T;

// Keywords - Transaction
BEGIN : B E G I N;
START : S T A R T;
TRANSACTION : T R A N S A C T I O N;
COMMIT : C O M M I T;
ROLLBACK : R O L L B A C K;
SAVEPOINT : S A V E P O I N T;

// Keywords - Other
AS : A S;
ASC : A S C;
DESC : D E S C;
DISTINCT : D I S T I N C T;
CASE : C A S E;
WHEN : W H E N;
THEN : T H E N;
ELSE : E L S E;
END : E N D;
IF : I F;
SHOW : S H O W;
DESCRIBE : D E S C R I B E;
EXPLAIN : E X P L A I N;
USE : U S E;
GRANT : G R A N T;
REVOKE : R E V O K E;
TO : T O;
TABLES : T A B L E S;
DATABASES : D A T A B A S E S;
COLUMNS : C O L U M N S;

// Aggregate functions
COUNT : C O U N T;
SUM : S U M;
AVG : A V G;
MIN : M I N;
MAX : M A X;

// String functions
CONCAT : C O N C A T;
SUBSTRING : S U B S T R I N G;
UPPER : U P P E R;
LOWER : L O W E R;
TRIM : T R I M;
LENGTH : L E N G T H;

// Date functions
NOW : N O W;
CURRENT_DATE : C U R R E N T '_' D A T E;
CURRENT_TIME : C U R R E N T '_' T I M E;
CURRENT_TIMESTAMP : C U R R E N T '_' T I M E S T A M P;

// Boolean literals
TRUE : T R U E;
FALSE : F A L S E;

// Operators
EQUAL : '=' | '==';
NOT_EQUAL : '!=' | '<>';
LESS_THAN : '<';
LESS_EQUAL : '<=';
GREATER_THAN : '>';
GREATER_EQUAL : '>=';

PLUS : '+';
MINUS : '-';
MULTIPLY : '*';
DIVIDE : '/';
MODULO : '%';

// Delimiters
SEMICOLON : ';';
COMMA : ',';
DOT : '.';
LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
LBRACKET : '[';
RBRACKET : ']';

// Identifiers
IDENTIFIER : [a-zA-Z_][a-zA-Z0-9_]*;
QUOTED_IDENTIFIER : '`' (~'`')+ '`';

// Literals
INTEGER_LITERAL : [0-9]+;
DECIMAL_LITERAL : [0-9]+ '.' [0-9]+;
STRING_LITERAL : '\'' (~['\r\n] | '\'\'' | '\\' .)* '\'';
DOUBLE_QUOTED_STRING : '"' (~["\r\n] | '""' | '\\' .)* '"';

// Error handling
UNEXPECTED_CHAR : .;

// Case-insensitive fragments
fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];
